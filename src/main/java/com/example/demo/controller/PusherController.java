package com.example.demo.controller;

import com.example.demo.utils.ChannelInfoFetcher;
import com.example.demo.dto.Hello;
import com.pusher.rest.Pusher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pusher")
public class PusherController {

    @Autowired
    private final Pusher pusher;

    public PusherController(Pusher pusher) {
        this.pusher = pusher;
    }


    //private channel endpoint
    @PostMapping("/messages")
    public void sendMessage(@RequestBody Hello message) {
        pusher.trigger("private-my-channel", "my-event", message);
    }


    //public channel endpoint
    @GetMapping("/randomApi")
    public void randomGetApiEndpoint() {
        pusher.trigger("rak-channel", "lul", "hello");
    }


    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(HttpServletRequest request, @RequestBody String channelData) {

        System.out.println("this is default endpoint");
        String socketId = request.getParameter("socket_id");
        System.out.println(socketId);
        String channelName = "private-my-channel";//request.getParameter("channel_name");
        System.out.println(channelName);
        // Check if the user is authorized to access the private chat room.
        // Implement your authentication and authorization logic here.

        System.out.println("custom authrization server endpoint");
//        pusher.trigger("private-channel2","myyevent","poo");
        boolean isAuthorized = true;
        if (isAuthorized) {
            String auth = pusher.authenticate(socketId, channelName);
            System.out.println("this is default end point auth--->"+auth);
            return ResponseEntity.ok(auth);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/triggerEvent")
    public ResponseEntity<String> triggerEvent() {
        // Prepare the event data
        Map<String, String> eventData = new HashMap<>();
        eventData.put("message", "This is a message from the server.");
        // Trigger the event on the private channel
        pusher.trigger("private-my-channel", "your_event_name", eventData);
        return ResponseEntity.ok("Event triggered.");
    }
    @GetMapping("/statusOfChannels")
    public void lol() throws Exception {
        ChannelInfoFetcher channelInfoFetcher = new ChannelInfoFetcher("1581017", "ea714da401dda1bea420", "945c4e772e80a2f703b5", "ap2");
        System.out.println(channelInfoFetcher.fetchChannelInfo("private-my-channel"));
        System.out.println(channelInfoFetcher.fetchChannelInfo("rak-channel"));

    }
    @PostMapping("/customAuthorization")
    public ResponseEntity<String> authenticate(HttpServletRequest request, @RequestBody Map<String, String> credentials) {


        //this the place where you check the username and password with respect to the database
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Check if the user is authorized to access the private channel
        boolean isAuthorized = true;
        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Get the socket ID and channel name from the request parameters
        String socketId = request.getParameter("socket_id");
        String channelName = request.getParameter("channel_name");

        System.out.println("this is the custom authorization endpoint");
        // Generate an auth token for the user
        String authToken = pusher.authenticate(socketId, channelName);
        System.out.println(authToken);
        return ResponseEntity.ok(authToken);
    }

    @GetMapping("/my-api")
    public ResponseEntity<String> myApi(@RequestHeader(name="Authorization", required=false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String authToken = authHeader.substring(7);
        System.out.println("-----------");
        System.out.println(authToken);


        // Validate the token and authorize the request
        boolean isAuthorized = true; ///like check if the token is expired or not...or do any other check
        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Do something with the token and return a response
        return ResponseEntity.ok("My API response");
    }




}

