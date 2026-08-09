package com.example.natureexplorer.data

import com.example.natureexplorer.network.CurrentWeather
import com.example.natureexplorer.network.WeatherApiService


class WeatherRepository(

    private val weatherApiService:
    WeatherApiService

) {

    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): CurrentWeather {

        return weatherApiService
            .getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
            .current
    }
}

