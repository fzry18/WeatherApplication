package com.example.weatherapplication.data.remote.weather

import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.remote.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("timeline/{location}")
    suspend fun getWeatherForecast(
        @Path("location") location: String,
        @Query("unitGroup") unitGroup: String = "metric", // Changed from "us" to "metric"
        @Query("include") include: String = "days,hours,current,alerts",
        @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("contentType") contentType: String = "json"
    ): Response<WeatherResponse>


}