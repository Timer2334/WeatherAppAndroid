package com.example.timer2334_20.domain.model

import com.example.timer2334_20.data.model.WeatherResponse


data class ForecastForUI(
    val weatherResponse: WeatherResponse,
    val temp: String,
    val icon: String,
    val time: Time
)

data class Time(
    val number: String,
    val weekdayOrRelative: String,
    val hours: String
)
