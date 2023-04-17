package com.example.demo;

import com.pusher.rest.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class DemoApplication {


//	Yes, your understanding is mostly correct. When you create a group chat application using Pusher,
//	a WebSocket connection is established between the client and the Pusher server.
//	Once this connection is in place, it remains open and active, allowing for real-time,
//	two-way communication between the client and server.
//
//	When a client publishes a message in the group chat,
//	it sends the message to the Pusher server through the established WebSocket connection,
//	without the need to make a separate HTTP request to an API endpoint.
//	The Pusher server then broadcasts the message to all the other clients who are connected to the same chat room,
//	again using the existing WebSocket connections.
//
//	While the WebSocket connections eliminate the need to constantly make new API requests for sending and receiving messages
//	in the chat, you still may need to interact with your own server-side APIs for tasks like user authentication,
//	storing messages in a database, or other application-specific features. The WebSocket connections provided by
//	Pusher mainly facilitate real-time communication between clients and the server for the chat functionality.

	public static void main(String[] args) {

//		Pusher pusher = new Pusher("1581017", "ea714da401dda1bea420", "945c4e772e80a2f703b5");
//		pusher.setCluster("ap2");
//		pusher.setEncrypted(true);
//		pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));
		SpringApplication.run(DemoApplication.class, args);
	}

}
