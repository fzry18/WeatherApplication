package com.example.weatherapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapplication.data.remote.WeatherResponse
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.ui.adapter.WeatherPagerAdapter
import com.example.weatherapplication.ui.weather.WeatherViewModel
import com.example.weatherapplication.utils.SharedPreferencesHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.example.weatherapplication.ui.forecast.ForecastFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<WeatherViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()
        setupObservers()
        setupViewPager()
        setupSearchLocation()

    }

    private fun setupViewPager() {
        binding.apply {
            val pagerAdapter = WeatherPagerAdapter(this@MainActivity)
            viewPager.adapter = pagerAdapter

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Prakiraan 7 Hari"
                    else -> "Tab ${position + 1}"
                }
            }.attach()
        }
    }

    private fun checkLocationPermission() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Tambahkan permintaan izin notifikasi untuk Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                LOCATION_PERMISSION_REQUEST_CODE  // Changed from PERMISSIONS_REQUEST_CODE
            )
        } else {
            fetchWeatherBasedOnLocation()
        }
    }

    private fun setupSearchLocation() {
        binding.apply {
            // Menangani klik tombol pencarian
            searchButton.setOnClickListener {
                performSearch()
            }

            // Menangani tekan "Enter" pada keyboard
            searchEditText.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                    performSearch()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchWeatherBasedOnLocation()
            } else {
                Toast.makeText(
                    this,
                    "Location permission is required to show weather for your location",
                    Toast.LENGTH_LONG
                ).show()
                // Fallback to default location if permission denied
                viewModel.fetchWeather("Jakarta")
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun fetchWeatherBasedOnLocation() {
        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, 1000L
        ).apply {
            setMinUpdateIntervalMillis(500L)
            setMaxUpdates(1)
        }.build()

        fusedLocationClient.requestLocationUpdates(locationRequest, object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Perbaiki penggunaan Geocoder untuk Android 13+
                    val geocoder = Geocoder(this@MainActivity, Locale.getDefault())

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // Pendekatan baru untuk Android 13+
                        geocoder.getFromLocation(latitude, longitude, 5) { addresses ->
                            if (addresses.isNotEmpty()) {
                                val address = addresses[0]
                                val cityName = address.locality ?: address.subAdminArea ?:
                                address.adminArea ?: "Unknown Location"
                                runOnUiThread {
                                    viewModel.fetchWeather(cityName)
                                }
                            } else {
                                // Fallback ke koordinat jika tidak ada hasil
                                runOnUiThread {
                                    viewModel.fetchWeather("Jakarta") // Lokasi default sebagai fallback
                                }
                            }
                        }
                    } else {
                        // Pendekatan lama untuk Android 12 dan di bawahnya
                        try {
                            val addresses = geocoder.getFromLocation(latitude, longitude, 5)
                            if (addresses != null && addresses.isNotEmpty()) {
                                val address = addresses[0]
                                val cityName = address.locality ?: address.subAdminArea ?:
                                address.adminArea ?: "Unknown Location"
                                viewModel.fetchWeather(cityName)
                            } else {
                                viewModel.fetchWeather("Jakarta") // Lokasi default sebagai fallback
                            }
                        } catch (e: Exception) {
                            Log.e("LOCATION_DEBUG", "Geocoding failed: ${e.message}")
                            viewModel.fetchWeather("Jakarta") // Lokasi default sebagai fallback
                        }
                    }
                } else {
                    handleLocationFallback()
                }
            }
        }, null)
    }

    private fun handleLocationFallback() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            Toast.makeText(this, "GPS dan Network Provider tidak aktif", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Tidak dapat menemukan lokasi. Menggunakan lokasi default.", Toast.LENGTH_LONG).show()
        }
        viewModel.fetchWeather("Tokyo") // Lokasi default
    }

    private fun setupObservers() {
        viewModel.weatherData.observe(this) { weatherResponse ->
            // Loading selalu berakhir ketika data berhasil diterima
            if (viewModel.isLoading.value == true) {
                viewModel.setLoadingState(false)
            }

            weatherResponse?.let {
                updateUI(it)
            }
        }

        // Hanya MainActivity yang menangani loading state
        viewModel.isLoading.observe(this) { isLoading ->
            binding.apply {
                // Atur visibility loading di MainActivity
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            // Koordinasi dengan fragment
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is ForecastFragment) {
                    fragment.setLoadingState(false) // Sembunyikan loading di fragment
                }
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            // Loading selalu berakhir ketika terjadi error
            if (viewModel.isLoading.value == true) {
                viewModel.setLoadingState(false)
            }

            errorMessage?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun performSearch() {
        binding.apply {
            val locationQuery = searchEditText.text.toString().trim()
            if (locationQuery.isNotEmpty()) {
                hideKeyboard()
                searchEditText.clearFocus()
                viewModel.fetchWeather(locationQuery)
            } else {
                Toast.makeText(this@MainActivity, "Masukkan nama kota terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard() {
        binding.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weatherResponse: WeatherResponse) {
        weatherResponse.currentConditions?.let { current ->
            binding.apply {
                // Gunakan resolvedAddress dari API jika tersedia
                locationTextView.text = weatherResponse.resolvedAddress ?: "Unknown Location"
                temperatureTextView.text = "${current.temp}°C"
                conditionTextView.text = current.conditions
                humidityTextView.text = "Humidity: ${current.humidity}%"
                windSpeedTextView.text = "Wind: ${current.windspeed} km/h"
            }

            // Simpan data cuaca terbaru ke SharedPreferences
            SharedPreferencesHelper.saveLastWeatherInfo(this, weatherResponse)
        }
    }
}