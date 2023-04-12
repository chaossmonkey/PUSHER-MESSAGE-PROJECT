package com.example.demo.dto;

public class Hello {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Hello(String lol) {
        this.message = lol;
    }

    public Hello() {
    }

    @Override
    public String toString() {
        return "Hello{" +
                "lol='" + message + '\'' +
                '}';
    }
}
