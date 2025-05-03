package com.example.weatherapplication.data.repository

import android.util.Log
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.remote.WeatherResponse
import com.example.weatherapplication.data.remote.weather.WeatherService

class WeatherRepository(private val weatherService: WeatherService) {

    suspend fun getWeather(location: String): Result<WeatherResponse> {
        Log.d("DEBUG_API_KEY", "API Key: ${BuildConfig.WEATHER_API_KEY}")
        return try {
            val response = weatherService.getWeatherForecast(location)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Data tidak ditemukan"))
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather: ${e.message}")
            Result.failure(e)
        }
    }

    companion object {
        // This is kept for backward compatibility but shouldn't be used with proper DI
        @Deprecated("Use constructor injection instead",
            ReplaceWith("Injection.provideWeatherRepository()"))
        fun getInstance(): WeatherRepository {
            return WeatherRepository(com.example.weatherapplication.data.remote.weather.WeatherConfig.getApiService())
        }
    }
}