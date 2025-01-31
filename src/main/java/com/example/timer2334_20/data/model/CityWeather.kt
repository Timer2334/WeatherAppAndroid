package com.example.timer2334_20.data.model

data class CityWeather(
    val name: String,
    val local_names: Map<String, String>,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)