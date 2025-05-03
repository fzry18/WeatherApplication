package com.example.weatherapplication.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.remote.WeatherResponse
import com.example.weatherapplication.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchWeather(location: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                repository.getWeather(location).fold(
                    onSuccess = { response ->
                        _weatherData.postValue(response)
                        _error.postValue(null)
                    },
                    onFailure = { exception ->
                        _error.postValue(exception.message ?: "Unknown error")
                        Log.e("WeatherViewModel", "Error: ${exception.message}")
                    }
                )
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
                Log.e("WeatherViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}