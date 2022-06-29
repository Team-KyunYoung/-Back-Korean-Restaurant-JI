package com.example.koreanrestaurantji.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/hello-world")
    public  String helloWorld() {
        return "hello-world";
    }
}
