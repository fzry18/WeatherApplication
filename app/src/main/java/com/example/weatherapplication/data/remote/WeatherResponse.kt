package com.example.weatherapplication.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
	@field:SerializedName("queryCost")
	val queryCost: Int? = null,

	@field:SerializedName("alerts")
	val alerts: List<Alert>? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("currentConditions")
	val currentConditions: CurrentConditions? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("days")
	val days: List<DaysItem?>? = null,

	@field:SerializedName("stations")
	val stations: Stations? = null,

	@field:SerializedName("tzoffset")
	val tzoffset: Double? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null,

	@field:SerializedName("resolvedAddress")
	val resolvedAddress: String? = null
)

data class Alert(
	@field:SerializedName("event")
	val event: String? = null,

	@field:SerializedName("headline")
	val headline: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("expires")
	val expires: Long? = null,

	@field:SerializedName("onset")
	val onset: Long? = null,

	@field:SerializedName("severity")
	val severity: String? = null
)

data class CurrentConditions(
	@field:SerializedName("uvindex")
	val uvindex: Double? = null,

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("preciptype")
	val preciptype: List<String?>? = null,

	@field:SerializedName("sunriseEpoch")
	val sunriseEpoch: Int? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("cloudcover")
	val cloudcover: Double? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("precip")
	val precip: Double? = null,

	@field:SerializedName("solarradiation")
	val solarradiation: Double? = null,

	@field:SerializedName("datetimeEpoch")
	val datetimeEpoch: Int? = null,

	@field:SerializedName("windgust")
	val windgust: Double? = null,

	@field:SerializedName("dew")
	val dew: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("precipprob")
	val precipprob: Double? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Double? = null,

	@field:SerializedName("feelslike")
	val feelslike: Double? = null,

	@field:SerializedName("winddir")
	val winddir: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("solarenergy")
	val solarenergy: Double? = null,

	@field:SerializedName("stations")
	val stations: List<String?>? = null,

	@field:SerializedName("moonphase")
	val moonphase: Double? = null,

	@field:SerializedName("snowdepth")
	val snowdepth: Double? = null,

	@field:SerializedName("snow")
	val snow: Double? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null,

	@field:SerializedName("sunsetEpoch")
	val sunsetEpoch: Int? = null,

	@field:SerializedName("windspeed")
	val windspeed: Double? = null,

	@field:SerializedName("conditions")
	val conditions: String? = null
)

data class WIHH(
	@field:SerializedName("contribution")
	val contribution: Double? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("useCount")
	val useCount: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null,

	@field:SerializedName("quality")
	val quality: Int? = null
)

data class Stations(
	@field:SerializedName("WIII")
	val wIII: WIII? = null,

	@field:SerializedName("WIHH")
	val wIHH: WIHH? = null
)

data class DaysItem(
	@field:SerializedName("uvindex")
	val uvindex: Double? = null,

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("preciptype")
	val preciptype: List<String?>? = null,

	@field:SerializedName("sunriseEpoch")
	val sunriseEpoch: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("feelslikemin")
	val feelslikemin: Double? = null,

	@field:SerializedName("cloudcover")
	val cloudcover: Double? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("precip")
	val precip: Double? = null,

	@field:SerializedName("solarradiation")
	val solarradiation: Double? = null,

	@field:SerializedName("datetimeEpoch")
	val datetimeEpoch: Int? = null,

	@field:SerializedName("windgust")
	val windgust: Double? = null,

	@field:SerializedName("dew")
	val dew: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("precipprob")
	val precipprob: Double? = null,

	@field:SerializedName("precipcover")
	val precipcover: Double? = null,

	@field:SerializedName("tempmin")
	val tempmin: Double? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("hours")
	val hours: List<HoursItem?>? = null,

	@field:SerializedName("feelslikemax")
	val feelslikemax: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Double? = null,

	@field:SerializedName("feelslike")
	val feelslike: Double? = null,

	@field:SerializedName("severerisk")
	val severerisk: Double? = null,

	@field:SerializedName("winddir")
	val winddir: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("solarenergy")
	val solarenergy: Double? = null,

	@field:SerializedName("stations")
	val stations: List<String?>? = null,

	@field:SerializedName("tempmax")
	val tempmax: Double? = null,

	@field:SerializedName("moonphase")
	val moonphase: Double? = null,

	@field:SerializedName("snowdepth")
	val snowdepth: Double? = null,

	@field:SerializedName("snow")
	val snow: Double? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null,

	@field:SerializedName("sunsetEpoch")
	val sunsetEpoch: Int? = null,

	@field:SerializedName("windspeed")
	val windspeed: Double? = null,

	@field:SerializedName("conditions")
	val conditions: String? = null
)

data class HoursItem(
	@field:SerializedName("uvindex")
	val uvindex: Double? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("preciptype")
	val preciptype: List<String?>? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("cloudcover")
	val cloudcover: Double? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("precip")
	val precip: Double? = null,

	@field:SerializedName("solarradiation")
	val solarradiation: Double? = null,

	@field:SerializedName("datetimeEpoch")
	val datetimeEpoch: Int? = null,

	@field:SerializedName("windgust")
	val windgust: Double? = null,

	@field:SerializedName("dew")
	val dew: Double? = null,

	@field:SerializedName("humidity")
	val humidity: Double? = null,

	@field:SerializedName("precipprob")
	val precipprob: Double? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("visibility")
	val visibility: Double? = null,

	@field:SerializedName("feelslike")
	val feelslike: Double? = null,

	@field:SerializedName("severerisk")
	val severerisk: Double? = null,

	@field:SerializedName("winddir")
	val winddir: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Double? = null,

	@field:SerializedName("solarenergy")
	val solarenergy: Double? = null,

	@field:SerializedName("stations")
	val stations: List<String?>? = null,

	@field:SerializedName("snowdepth")
	val snowdepth: Double? = null,

	@field:SerializedName("snow")
	val snow: Double? = null,

	@field:SerializedName("windspeed")
	val windspeed: Double? = null,

	@field:SerializedName("conditions")
	val conditions: String? = null
)

data class WIII(
	@field:SerializedName("contribution")
	val contribution: Double? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("useCount")
	val useCount: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null,

	@field:SerializedName("quality")
	val quality: Int? = null
)