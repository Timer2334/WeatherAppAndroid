package com.example.timer2334_20.domain.model

enum class Temperature {
    METRIC,
    IMPERIAL
}

enum class ThemeConfig {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK
}

enum class WindSpeed(
    val value: String
) {
    METERS_PER_SECOND("м/с"),
    KILOMETERS_PER_HOUR("км/ч"),
    MILES_PER_HOUR("миль/ч"),
    KNOTS("узлы")
}