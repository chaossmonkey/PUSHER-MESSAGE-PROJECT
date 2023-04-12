package com.example.demo;

import com.pusher.rest.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

//		Pusher pusher = new Pusher("1581017", "ea714da401dda1bea420", "945c4e772e80a2f703b5");
//		pusher.setCluster("ap2");
//		pusher.setEncrypted(true);
//		pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));
		SpringApplication.run(DemoApplication.class, args);
	}

}
