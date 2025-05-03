package com.example.weatherapplication.di

import com.example.weatherapplication.data.remote.weather.WeatherConfig
import com.example.weatherapplication.data.remote.weather.WeatherService
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.ui.weather.WeatherViewModel

/**
 * Provides dependencies for the application using manual dependency injection.
 * This class centralizes dependency creation to make the code more maintainable
 * and testable.
 */
object Injection {
    /**
     * Provides a WeatherRepository instance with proper dependencies.
     */
    fun provideWeatherRepository(): WeatherRepository {
        val weatherService = provideWeatherService()
        return WeatherRepository(weatherService)
    }

    /**
     * Provides a WeatherService instance.
     */
    private fun provideWeatherService(): WeatherService {
        return WeatherConfig.getApiService()
    }

    /**
     * Provides a WeatherViewModel instance with proper dependencies.
     */
    fun provideWeatherViewModel(): WeatherViewModel {
        val repository = provideWeatherRepository()
        return WeatherViewModel(repository)
    }
}