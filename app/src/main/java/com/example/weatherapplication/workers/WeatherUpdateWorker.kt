package com.example.weatherapplication.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.di.Injection
import com.example.weatherapplication.utils.NotificationHelper
import com.example.weatherapplication.utils.SharedPreferencesHelper
import kotlin.math.abs

class WeatherUpdateWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "WeatherUpdateWorker"
        private const val SIGNIFICANT_TEMP_CHANGE = 5.0f // Perubahan suhu yang dianggap signifikan (dalam Celsius)
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Checking for weather updates...")

        try {
            // Ambil lokasi terakhir dari SharedPreferences
            val lastLocation = SharedPreferencesHelper.getLastLocation(context)
            if (lastLocation.isBlank()) {
                Log.d(TAG, "No last location found. Skipping weather check.")
                return Result.success()
            }

            // Ambil data cuaca terbaru
            val repository = Injection.provideWeatherRepository()
            val result = repository.getWeather(lastLocation)

            result.fold(
                onSuccess = { currentWeather ->
                    // Ambil data cuaca yang disimpan sebelumnya
                    val lastTemperature = SharedPreferencesHelper.getLastTemperature(context)
                    val lastConditions = SharedPreferencesHelper.getLastConditions(context)

                    // Ambil data cuaca saat ini
                    val currentTemperature = currentWeather.currentConditions?.temp?.toFloat() ?: 0f
                    val currentConditions = currentWeather.currentConditions?.conditions ?: ""

                    // Periksa perubahan signifikan
                    val tempDiff = abs(currentTemperature - lastTemperature)
                    val conditionsChanged = currentConditions != lastConditions && currentConditions.isNotBlank()

                    if (tempDiff >= SIGNIFICANT_TEMP_CHANGE || conditionsChanged) {
                        Log.d(TAG, "Significant weather change detected. Showing notification.")

                        // Buat pesan notifikasi yang sesuai
                        val title = "Perubahan Cuaca di $lastLocation"
                        val message = buildNotificationMessage(
                            lastTemperature, currentTemperature,
                            lastConditions, currentConditions
                        )

                        // Tampilkan notifikasi
                        NotificationHelper.showWeatherNotification(context, title, message)
                    } else {
                        Log.d(TAG, "No significant weather changes.")
                    }

                    // Perbarui data cuaca terakhir
                    SharedPreferencesHelper.saveLastWeatherInfo(context, currentWeather)
                },
                onFailure = { exception ->
                    Log.e(TAG, "Error fetching weather data: ${exception.message}")
                }
            )

            return Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Background work failed: ${e.message}")
            return Result.failure()
        }
    }

    private fun buildNotificationMessage(
        lastTemp: Float,
        currentTemp: Float,
        lastConditions: String,
        currentConditions: String
    ): String {
        val tempDiff = currentTemp - lastTemp
        val tempChangeMsg = when {
            tempDiff > 0 -> "Suhu naik ${String.format("%.1f", tempDiff)}°C"
            tempDiff < 0 -> "Suhu turun ${String.format("%.1f", abs(tempDiff))}°C"
            else -> "Suhu tetap ${String.format("%.1f", currentTemp)}°C"
        }

        val conditionsMsg = if (lastConditions != currentConditions && currentConditions.isNotBlank()) {
            "Kondisi berubah menjadi $currentConditions"
        } else {
            ""
        }

        return if (conditionsMsg.isBlank()) {
            tempChangeMsg
        } else {
            "$tempChangeMsg. $conditionsMsg"
        }
    }
}