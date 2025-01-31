package com.example.timer2334_20.domain

import com.example.timer2334_20.data.WeatherServerOperation
import com.example.timer2334_20.data.model.Coord
import com.example.timer2334_20.data.model.Forecast
import com.example.timer2334_20.data.model.ForecastData
import com.example.timer2334_20.data.model.WeatherResponse
import com.example.timer2334_20.domain.model.DailyForecasts
import com.example.timer2334_20.domain.model.ForecastForUI
import com.example.timer2334_20.domain.model.Time
import com.example.timer2334_20.domain.model.UserSettingsData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

object WeatherRepository {
    private var Data: Pair<WeatherResponse, ForecastData>? = null
    
    suspend fun collectionData(userSettingsData: UserSettingsData) {
        Data = WeatherServerOperation.getWeatherInfo(
            cityName = userSettingsData.cityName,
            temperatureUnit = userSettingsData.temperature.name.lowercase()
        )
    }
    
    fun getWeatherData(): WeatherResponse? {
        return Data?.first
    }
    
    fun getForecastData(): DailyForecasts<ForecastForUI> {
        val x = Data?.second?.list?.map {
            creatingForecastForUI(it)
        }
        return forecastProcessing(x)
    }
}

private fun forecastProcessing(list: List<ForecastForUI>?): DailyForecasts<ForecastForUI> {
    if (list == null) return DailyForecasts()
    val dailyForecasts = DailyForecasts<ForecastForUI>()
    val currentTime = LocalDateTime.now()
    val formatDate = DateTimeFormatter.ofPattern("d")
    val days = (0..5).map {
        currentTime.plusDays(it.toLong()).format(formatDate)
    }
    list.forEach {
        val forecastDate = it.time.number
        when (forecastDate) {
            days[0] -> addForecastForDay(dailyForecasts, "today", it)
            days[1] -> addForecastForDay(dailyForecasts, "secondDay", it)
            days[2] -> addForecastForDay(dailyForecasts, "thirdDay", it)
            days[3] -> addForecastForDay(dailyForecasts, "fourthDay", it)
            days[4] -> addForecastForDay(dailyForecasts, "fifthDay", it)
            days[5] -> addForecastForDay(dailyForecasts, "sixthDay", it)
        }
    }
    return dailyForecasts
}

private fun addForecastForDay(
    dailyForecasts: DailyForecasts<ForecastForUI>,
    dayName: String,
    forecastForUI: ForecastForUI
) {
    val particularDay: MutableList<ForecastForUI> = when (dayName) {
        "today" -> dailyForecasts.today
        "secondDay" -> dailyForecasts.secondDay
        "thirdDay" -> dailyForecasts.thirdDay
        "fourthDay" -> dailyForecasts.fourthDay
        "fifthDay" -> dailyForecasts.fifthDay
        "sixthDay" -> dailyForecasts.sixthDay
        else -> throw IllegalArgumentException("Неверное имя дня: $dayName.")
    }
    if (particularDay.isEmpty()) {
        dailyForecasts.list.add(forecastForUI)
    }
    particularDay.add(forecastForUI)
}

private fun formatDateTime(
    epochSeconds: Long,
    pattern: String
): String {
    val instant = Instant.ofEpochSecond(epochSeconds)
    val timeInUtc = LocalDateTime.ofInstant(instant, ZoneId.of("UTC+5"))
    val complete = DateTimeFormatter.ofPattern(
        pattern,
        Locale("ru")
    )
    return timeInUtc.format(complete)
}

private fun formatWeekdayOrRelative(
    epochSeconds: Long,
): String {
    val localDateTime = LocalDateTime.now()
    val timeNowInUTC = localDateTime.toEpochSecond(ZoneOffset.of("+05:00"))
    val timeNow = formatDateTime(timeNowInUTC, "d")
    val timeInput = formatDateTime(epochSeconds, "d")
    return if (timeNow == timeInput) {
        "сегодня"
    } else if (timeNow.toInt() + 1 == timeInput.toInt()) {
        "завтра"
    } else {
        formatDateTime(epochSeconds, "eeee")
    }
}

private fun creatingForecastForUI(forecast: Forecast): ForecastForUI {
    return ForecastForUI(
        weatherResponse = WeatherResponse(
            Coord(0.0, 0.0),
            forecast.weather,
            "",
            forecast.main,
            forecast.visibility,
            forecast.wind,
            null,
            forecast.clouds,
            forecast.dt,
            forecast.sys,
            0,
            0,
            "",
            0
        ),
        temp = forecast.main.temp.toInt().toString(),
        icon = forecast.weather[0].icon,
        time = Time(
            number = formatDateTime(forecast.dt, "d"),
            weekdayOrRelative = formatWeekdayOrRelative(forecast.dt),
            hours = formatDateTime(forecast.dt, "HH:mm")
        )
    )
}
