package com.example.demo.config;

import com.pusher.rest.Pusher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class Config {

    @Bean
    public Pusher getme()
    {
        Pusher pusher = new Pusher("1581017", "ea714da401dda1bea420", "945c4e772e80a2f703b5");
        pusher.setCluster("ap2");
        pusher.setEncrypted(true);

//        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));
        return pusher;
    }

}
