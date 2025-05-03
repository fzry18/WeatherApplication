package com.example.weatherapplication.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.data.remote.DaysItem
import com.example.weatherapplication.databinding.ItemForecastBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastAdapter : ListAdapter<DaysItem, ForecastAdapter.ForecastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        if (forecast != null) {
            holder.bind(forecast)
        }
    }

    class ForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale")
        fun bind(forecast: DaysItem) {
            try {
                // Format tanggal dari "yyyy-MM-dd" ke "EEE, dd MMM"
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale("id"))

                forecast.datetime?.let {
                    val date = dateFormat.parse(it)
                    date?.let { parsedDate ->
                        binding.tvDate.text = outputFormat.format(parsedDate)
                    }
                }

                // Format suhu dengan format yang lebih ringkas untuk layar kecil
                binding.tvTemperature.text = String.format("%.1f°/%.1f°",
                    forecast.tempmax ?: 0.0,
                    forecast.tempmin ?: 0.0)

                binding.tvConditions.text = forecast.conditions
            } catch (e: Exception) {
                Log.e("ForecastAdapter", "Error binding data: ${e.message}")
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DaysItem>() {
            override fun areItemsTheSame(oldItem: DaysItem, newItem: DaysItem): Boolean {
                return oldItem.datetime == newItem.datetime
            }

            override fun areContentsTheSame(oldItem: DaysItem, newItem: DaysItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}