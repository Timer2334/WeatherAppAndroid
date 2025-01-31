package com.example.timer2334_20.domain.model

data class UserSettingsData(
    val cityName: String,
    var temperature: Temperature,
    var windSpeed: WindSpeed,
    var themeConfig: ThemeConfig
    //val pressure: String = "",
)
