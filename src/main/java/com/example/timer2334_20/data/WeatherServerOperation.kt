package com.example.timer2334_20.data

import com.example.timer2334_20.data.model.ForecastData
import com.example.timer2334_20.data.model.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherServerOperation {
    private var retrofit: Retrofit
    private val weatherResponseApi: WeatherResponseApi
    
    init {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        weatherResponseApi = retrofit.create(WeatherResponseApi::class.java)
    }
    
    suspend fun getWeatherInfo(
        cityName: String,
        temperatureUnit: String,
    ): Pair<WeatherResponse, ForecastData> {
        val coordinates = weatherResponseApi.getCityWeather(cityName)[0]
        // сохранять кооординаты в памяти, чтобы меньше делать запросы к серверу
        val weather = weatherResponseApi.getWeatherResponse(
            lat = bugInLonAndLat(coordinates.lat, "lat"),
            lon = bugInLonAndLat(coordinates.lon, "lon"),
            units = temperatureUnit
        )
        val forecast = weatherResponseApi.getForecastData(
            lat = bugInLonAndLat(coordinates.lat, "lat"),
            lon = bugInLonAndLat(coordinates.lon, "lon"),
            units = temperatureUnit
        )
        return Pair(weather, forecast)
    }
    
//    suspend fun getWeatherInfo(temperatureUnit: String): WeatherResponse {
//        val coord = weatherResponseApi.getCityWeather("Уфа")
//        val weather = weatherResponseApi.getWeatherResponse(
//            lat = if (coord[0].lat == 54.7261409) 54.775 else coord[0].lat,
//            lon = if (coord[0].lon == 55.947499) 56.0375 else coord[0].lon,
//            units = temperatureUnit
//        )
//        return weather
//    }
//
//    suspend fun getForecastData(temperatureUnit: String): ForecastData {
//        val coord = weatherResponseApi.getCityWeather("Уфа")
//        val forecast = weatherResponseApi.getForecastData(
//            lat = bugInLonAndLat(coord[0].lat, "lat"),
//            lon = bugInLonAndLat(coord[0].lon, "lon"),
//            units = temperatureUnit
//        )
//        return forecast
//    }
    
    private fun bugInLonAndLat(coordinates: Double, type: String): Double {
        return when (type) {
            "lat" -> if (coordinates == 54.7261409) 54.775 else coordinates
            else -> if (coordinates == 55.947499) 56.0375 else coordinates
        }
    }
}