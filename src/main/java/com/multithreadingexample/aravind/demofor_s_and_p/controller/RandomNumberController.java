package com.multithreadingexample.aravind.demofor_s_and_p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multithreadingexample.aravind.demofor_s_and_p.service.Multithreadingserice;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class RandomNumberController {

    private final Random random = new Random();
    @Autowired
    public Multithreadingserice multithreadingserice;

    @GetMapping(value = "/random")
    public int getRandomNumber() {
        return generateRandomNumber();
    }

    private int generateRandomNumber() {
        return random.nextInt(100);  
    }
       @GetMapping(value = "/thread")
    public void runThread() throws Exception {
         multithreadingserice.run();
    }

}

