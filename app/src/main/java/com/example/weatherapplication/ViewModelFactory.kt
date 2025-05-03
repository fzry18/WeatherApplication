package com.example.weatherapplication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.di.Injection
import com.example.weatherapplication.ui.weather.WeatherViewModel

/**
 * Factory class for creating WeatherViewModel with the necessary dependencies.
 * This follows the Factory pattern for proper dependency injection.
 */
class ViewModelFactory private constructor(
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WeatherViewModel::class.java) -> {
                WeatherViewModel(weatherRepository) as T
            }
            // Tambahkan ViewModel lain di sini jika diperlukan
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideWeatherRepository()
                ).also { INSTANCE = it }
            }
        }
    }
}