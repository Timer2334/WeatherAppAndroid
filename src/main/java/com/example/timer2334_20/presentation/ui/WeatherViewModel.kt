package com.example.timer2334_20.presentation.ui

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer2334_20.data.DataStore
import com.example.timer2334_20.data.model.WeatherResponse
import com.example.timer2334_20.domain.WeatherRepository
import com.example.timer2334_20.domain.model.DailyForecasts
import com.example.timer2334_20.domain.model.ForecastForUI
import com.example.timer2334_20.domain.model.UserSettingsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class WeatherViewModel(context: Context) : ViewModel() {
    private val dataStore = DataStore(context)
    
    private val _userData = MutableStateFlow<UserSettingsData?>(null)
    val userData: StateFlow<UserSettingsData?> = _userData.asStateFlow()
    
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState.asStateFlow()
    
    private val _forecastState = MutableStateFlow<DailyForecasts<ForecastForUI>?>(null)
    val forecastState: StateFlow<DailyForecasts<ForecastForUI>?> = _forecastState.asStateFlow()
    
    fun serverRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userData.value?.let {
                    WeatherRepository.collectionData(it)
                }
                _weatherState.value = WeatherRepository.getWeatherData()
                _forecastState.value = WeatherRepository.getForecastData()
            } catch (e: Exception) {
                Log.e("t", "WeatherViewModel", e)
            }
        }
    }
    
    fun changeData(
        key: Preferences.Key<String>,
        value: String
    ) {
        viewModelScope.launch {
            dataStore.changeData(key, value)
        }
    }
    
    fun changeCheck() {
        viewModelScope.launch {
            dataStore.getData().collect { settingsData ->
                _userData.value = settingsData
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            userData
                .filterNotNull()
                .distinctUntilChanged { old, new ->
                old.temperature == new.temperature }
                .collect {
                serverRequest()
            }
        }
    }
}

    

