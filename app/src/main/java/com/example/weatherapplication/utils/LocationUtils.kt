package com.example.weatherapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.util.Log

object LocationUtils {
    @SuppressLint("MissingPermission") // Permission sudah dicek sebelumnya
    fun getLastKnownLocation(context: Context): Location? {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

        // Coba dapatkan lokasi dari GPS dan Network Provider
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
        for (provider in providers) {
            try {
                return locationManager.getLastKnownLocation(provider)
            } catch (e: Exception) {
                Log.e("LocationUtils", "Error dari $provider: ${e.message}")
            }
        }
        return null
    }
}