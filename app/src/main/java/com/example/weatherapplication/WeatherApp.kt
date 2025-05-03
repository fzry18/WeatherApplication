package com.example.weatherapplication

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapplication.utils.NotificationHelper
import com.example.weatherapplication.workers.WeatherUpdateWorker
import java.util.concurrent.TimeUnit

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inisialisasi notification channel
        NotificationHelper.createNotificationChannel(this)

        // Jadwalkan worker untuk mengecek cuaca secara berkala
        scheduleWeatherUpdateWork()
    }

    private fun scheduleWeatherUpdateWork() {
        // Buat permintaan kerja periodik untuk memeriksa cuaca setiap 1 jam
        val weatherWorkRequest = PeriodicWorkRequestBuilder<WeatherUpdateWorker>(
            1, TimeUnit.HOURS, // Interval minimum untuk PeriodicWorkRequest
            15, TimeUnit.MINUTES  // Flex interval
        ).build()

        // Jadwalkan kerja - ganti yang sudah ada jika ada
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "weather_update_work",
            ExistingPeriodicWorkPolicy.UPDATE,  // Update existing work
            weatherWorkRequest
        )
    }
}