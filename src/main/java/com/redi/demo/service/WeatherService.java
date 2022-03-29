package com.redi.demo.service;

import com.redi.demo.client.Weather;
import com.redi.demo.client.WeatherApi;
import com.redi.demo.client.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class WeatherService {

    private static Double BERLIN_LAT = 52.5067614;

    private static Double BERLIN_LON = 13.2846504;

    private static String DEFAULT_RESPONSE = "very good";

    private WeatherApi weatherApi;

    @Autowired
    public WeatherService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public String getWeather() {
        try {
            Response<WeatherResponse> response = weatherApi.getWeather(BERLIN_LAT, BERLIN_LON).execute();
            if(response.isSuccessful()) {
                WeatherResponse weatherResponse = response.body();
                Weather weather = weatherResponse.getWeathers().get(0);
                if(weather != null) {
                    return weather.getMain();
                } else {
                    return DEFAULT_RESPONSE;
                }
            } else {
                System.out.println("there was an error" + response.errorBody());
                return DEFAULT_RESPONSE;
            }

        } catch (Exception e) {
            return DEFAULT_RESPONSE;
        }

    }
}
