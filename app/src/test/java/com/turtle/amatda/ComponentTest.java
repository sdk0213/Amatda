package com.turtle.amatda;

import com.turtle.amatda.data.api.WeatherAPIService;

import org.junit.Test;

import javax.inject.Inject;

public class ComponentTest {

    @Inject WeatherAPIService WeatherAPIService;

    @Test
    public void testSingletonComponent() {

        WeatherAPIService.getWeather()
                .subscribe(
                        {},
                        {}
                )

    }

}
