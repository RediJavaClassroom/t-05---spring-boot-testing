package com.redi.demo.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class WeatherApiConfig {

    private static String BASE_URL = "https://fcc-weather-api.glitch.me/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    @Bean
    public WeatherApi weatherApi() {
        return retrofit.create(WeatherApi.class);
    }
}
