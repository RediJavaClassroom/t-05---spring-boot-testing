package com.redi.demo.controller;

import com.redi.demo.domain.Greeting;
import com.redi.demo.domain.GreetingStatistic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    private final Map<String, Long> statistics = new HashMap<>();

    @GetMapping("greeting")
    public Greeting greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        storeGreetStatistic(name);
        return new Greeting(counter.incrementAndGet(), "Hello, " + name + "!");
    }

    private void storeGreetStatistic(String name) {
        long greets = statistics.getOrDefault(name, 0L) + 1;
        statistics.put(name, greets);
    }

    @GetMapping("statistics")
    public List<GreetingStatistic> greet() {
        List<GreetingStatistic> greetingStatistics = new ArrayList<>();
        for(Map.Entry<String, Long> entry: statistics.entrySet()) {
            GreetingStatistic greetingStatistic = new GreetingStatistic(entry.getKey(), entry.getValue());
            greetingStatistics.add(greetingStatistic);
        }
        return greetingStatistics;
    }
}
