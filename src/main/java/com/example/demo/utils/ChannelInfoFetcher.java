package com.example.demo.utils;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

// ...

public class ChannelInfoFetcher {
    private final OkHttpClient client;
    private final String pusherAppId;
    private final String pusherKey;
    private final String pusherSecret;
    private final String pusherCluster;

    public ChannelInfoFetcher(String pusherAppId, String pusherKey, String pusherSecret, String pusherCluster) {
        this.pusherAppId = pusherAppId;
        this.pusherKey = pusherKey;
        this.pusherSecret = pusherSecret;
        this.pusherCluster = pusherCluster;
        this.client = new OkHttpClient();
    }

    private String createAuthSignature(String method, String requestPath, String queryString) {
        try {
            String signData = method + "\n" + requestPath + "\n" + queryString;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(pusherSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(signData.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(hashBytes.length * 2);
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to create authentication signature", e);
        }
    }

    public JSONObject fetchChannelInfo(String channelName) throws JSONException {
        String baseUrl = "https://api-" + pusherCluster + ".pusher.com";
        String requestPath = "/apps/" + pusherAppId + "/channels/" + channelName;
        String url = baseUrl + requestPath;

        long timestamp = Instant.now().getEpochSecond();
        String queryString = "auth_key=" + pusherKey + "&auth_timestamp=" + timestamp + "&auth_version=1.0";
        String authSignature = createAuthSignature("GET", requestPath, queryString);
        queryString += "&auth_signature=" + authSignature;

        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder().encodedQuery(queryString).build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return new JSONObject(responseBody);
            } else {
                System.out.println("Failed to fetch channel info: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
