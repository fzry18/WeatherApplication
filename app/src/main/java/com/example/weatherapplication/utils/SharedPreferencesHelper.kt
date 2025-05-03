package com.example.weatherapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapplication.data.remote.WeatherResponse
import com.google.gson.Gson

object SharedPreferencesHelper {
    private const val PREF_NAME = "weather_preferences"
    private const val KEY_LAST_TEMPERATURE = "last_temperature"
    private const val KEY_LAST_CONDITIONS = "last_conditions"
    private const val KEY_LAST_LOCATION = "last_location"
    private const val KEY_LAST_WEATHER_DATA = "last_weather_data"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLastWeatherInfo(context: Context, weatherResponse: WeatherResponse) {
        val temperature = weatherResponse.currentConditions?.temp ?: return
        val conditions = weatherResponse.currentConditions?.conditions ?: return
        val location = weatherResponse.resolvedAddress ?: return

        getPreferences(context).edit().apply {
            putFloat(KEY_LAST_TEMPERATURE, temperature.toFloat())
            putString(KEY_LAST_CONDITIONS, conditions)
            putString(KEY_LAST_LOCATION, location)
            // Serialize full response for future comparison if needed
            putString(KEY_LAST_WEATHER_DATA, Gson().toJson(weatherResponse))
            apply()
        }
    }

    fun getLastTemperature(context: Context): Float {
        return getPreferences(context).getFloat(KEY_LAST_TEMPERATURE, 0f)
    }

    fun getLastConditions(context: Context): String {
        return getPreferences(context).getString(KEY_LAST_CONDITIONS, "") ?: ""
    }

    fun getLastLocation(context: Context): String {
        return getPreferences(context).getString(KEY_LAST_LOCATION, "") ?: ""
    }

    fun getLastWeatherData(context: Context): WeatherResponse? {
        val json = getPreferences(context).getString(KEY_LAST_WEATHER_DATA, null) ?: return null
        return try {
            Gson().fromJson(json, WeatherResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}