package com.example.timer2334_20.data.model


data class ForecastData(
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)

data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val dt_txt: String
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)