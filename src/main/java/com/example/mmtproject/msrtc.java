package com.example.mmtproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class msrtc {

    @GetMapping("/msrtc")
    public String getData(){
        return "this is msrtc page";
    }
}
