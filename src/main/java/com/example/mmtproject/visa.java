package com.example.mmtproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class visa {

    @GetMapping("/visa")
    public String getData(){
        return "Please book your flight at 10% discount";
    }
}