package com.redi.demo.controller;

import com.redi.demo.domain.Greeting;
import com.redi.demo.domain.GreetingStatistic;
import com.redi.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {

    GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("greeting")
    public Greeting greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return greetingService.greet(name);
    }

    @GetMapping("statistics")
    public List<GreetingStatistic> statistics() {
        return greetingService.getStatistics();
    }
}
