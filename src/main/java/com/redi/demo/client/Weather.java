package com.redi.demo.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    String main;
    String description;
    URL icon;

    public Weather() {
    }

    public Weather(String main, String description, URL icon) {
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public URL getIcon() {
        return icon;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(URL icon) {
        this.icon = icon;
    }
}
