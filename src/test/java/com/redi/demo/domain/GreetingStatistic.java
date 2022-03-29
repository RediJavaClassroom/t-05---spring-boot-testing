package com.redi.demo.domain;

public class GreetingStatistic {

    private String name;
    private long greets;

    public GreetingStatistic(String name, long greets) {
        this.name = name;
        this.greets = greets;
    }

    public String getName() {
        return name;
    }

    public long getGreets() {
        return greets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGreets(long greets) {
        this.greets = greets;
    }
}
