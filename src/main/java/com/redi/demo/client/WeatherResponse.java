package com.redi.demo.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    String name;
    @JsonProperty("weather")
    List<Weather> weathers;

    public WeatherResponse() {
    }

    public WeatherResponse(String name, List<Weather> weathers) {
        this.name = name;
        this.weathers = weathers;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }
}