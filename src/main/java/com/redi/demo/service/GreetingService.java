package com.redi.demo.service;

import com.redi.demo.domain.Greeting;
import com.redi.demo.domain.GreetingStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService {

    private final AtomicLong counter = new AtomicLong();

    private final Map<String, Long> statistics = new HashMap<>();

    private static final int MAX_SIZE = 10;

    private WeatherService weatherService;

    @Autowired
    public GreetingService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public Greeting greet(String name) {
        validateGreetSize(name);
        storeGreetStatistic(name);
        return new Greeting(counter.incrementAndGet(), "Hello, " + name + "!, today the weather is: " + weatherService.getWeather());
    }

    public List<GreetingStatistic> getStatistics() {
        List<GreetingStatistic> greetingStatistics = new ArrayList<>();
        for(Map.Entry<String, Long> entry: statistics.entrySet()) {
            GreetingStatistic greetingStatistic = new GreetingStatistic(entry.getKey(), entry.getValue());
            greetingStatistics.add(greetingStatistic);
        }
        return greetingStatistics;
    }

    private void validateGreetSize(String name) {
        if(name.length() > MAX_SIZE) {
            throw new RuntimeException("Sorry, we can only hold greets of max size" + MAX_SIZE);
        }
    }

    private void storeGreetStatistic(String name) {
        long greets = statistics.getOrDefault(name, 0L) + 1;
        statistics.put(name, greets);
    }
}
