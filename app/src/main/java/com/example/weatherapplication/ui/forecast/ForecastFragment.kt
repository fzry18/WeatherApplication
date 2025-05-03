package com.example.weatherapplication.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.ViewModelFactory
import com.example.weatherapplication.databinding.FragmentForecastBinding
import com.example.weatherapplication.ui.adapter.ForecastAdapter
import com.example.weatherapplication.ui.weather.WeatherViewModel

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<WeatherViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val forecastAdapter = ForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeWeatherData()
    }

    fun setLoadingState(isLoading: Boolean) {
        _binding?.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun observeWeatherData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            // Pastikan loading state berhenti ketika data diterima
            setLoadingState(false)

            weatherResponse?.days?.let { days ->
                // Filter out null items and submit to adapter
                forecastAdapter.submitList(days.filterNotNull())
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvForecast.layoutManager = LinearLayoutManager(requireContext())
            rvForecast.adapter = forecastAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}