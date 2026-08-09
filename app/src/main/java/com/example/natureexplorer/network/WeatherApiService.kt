package com.example.natureexplorer.network

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class WeatherResponse(

    val latitude: Double,

    val longitude: Double,

    val current: CurrentWeather
)


data class CurrentWeather(

    val time: String,

    @SerializedName("temperature_2m")
    val temperature: Double,

    @SerializedName("relative_humidity_2m")
    val humidity: Int,

    @SerializedName("wind_speed_10m")
    val windSpeed: Double,

    @SerializedName("weather_code")
    val weatherCode: Int
)


interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getCurrentWeather(

        @Query("latitude")
        latitude: Double,

        @Query("longitude")
        longitude: Double,

        @Query("current")
        current: String =
            "temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code",

        @Query("timezone")
        timezone: String = "auto"

    ): WeatherResponse
}


object WeatherApiClient {

    private const val BASE_URL =
        "https://api.open-meteo.com/"


    val apiService: WeatherApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(
                WeatherApiService::class.java
            )
    }
}

