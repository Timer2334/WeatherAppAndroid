package com.example.timer2334_20.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.timer2334_20.domain.model.Temperature
import com.example.timer2334_20.domain.model.ThemeConfig
import com.example.timer2334_20.domain.model.WindSpeed
import com.example.timer2334_20.domain.model.UserSettingsData
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data")

val CITYNAME_KEY = stringPreferencesKey("cityName")
val TEMPERATURE_KEY = stringPreferencesKey("temp")
val WINDSPEED_KEY = stringPreferencesKey("windForce")
val PRESSURE_KEY = stringPreferencesKey("pressure")
val DARKTHEMECONFIG_KEY = stringPreferencesKey("darkThemeConfig")

class DataStore(
    val context: Context
) {
    suspend fun changeData(
        key: Preferences.Key<String>,
        value: String
    ) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[key] = value
        }
    }
    
    fun getData() = context.dataStore.data.map { preferences ->
        return@map UserSettingsData(
            cityName = preferences[CITYNAME_KEY] ?: "Уфа",
            temperature = Temperature.valueOf(
                preferences[TEMPERATURE_KEY] ?: Temperature.METRIC.name
            ),
            windSpeed = WindSpeed.valueOf(
                preferences[WINDSPEED_KEY] ?: WindSpeed.METERS_PER_SECOND.name
            ),
            themeConfig = ThemeConfig.valueOf(
                preferences[DARKTHEMECONFIG_KEY]
                    ?: ThemeConfig.FOLLOW_SYSTEM.name
            ),
            
            //preferences[WINDFORCE_KEY] ?: WindSpeed.METERS_PER_SECOND,
            //preferences[PRESSURE_KEY] ?: "",
            //preferences[DARKTHEMECONFIG_KEY] ?: ,
        )
    }
}


