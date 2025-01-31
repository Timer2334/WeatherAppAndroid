package com.example.timer2334_20.presentation.ui.components.mainWindow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.timer2334_20.presentation.ui.WeatherViewModel
import com.example.timer2334_20.presentation.ui.components.mainWindow.content.DisplayForecast
import com.example.timer2334_20.presentation.ui.components.mainWindow.content.TopDisplayWeather

@Composable
fun Content(
    viewModel: WeatherViewModel,
    innerPadding: PaddingValues
) {
    val weatherResponse by viewModel.weatherState.collectAsState()
    val dailyForecasts by viewModel.forecastState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopDisplayWeather(
            weatherResponse = weatherResponse,
            innerPadding = innerPadding
        )
        DisplayForecast(
            dailyForecasts = dailyForecasts
        )
    }
}