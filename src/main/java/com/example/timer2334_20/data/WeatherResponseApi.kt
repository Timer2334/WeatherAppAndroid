package com.example.timer2334_20.data

import com.example.timer2334_20.data.model.CityWeather
import com.example.timer2334_20.data.model.ForecastData
import com.example.timer2334_20.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherResponseApi {
    
    @GET("data/2.5/weather")
    suspend fun getWeatherResponse(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String = "ru"
    ): WeatherResponse
    
    @GET("geo/1.0/direct")
    suspend fun getCityWeather(
        @Query("q") cityName: String = "Уфа",
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String,
    ): List <CityWeather>
    
    @GET("data/2.5/forecast")
    suspend fun getForecastData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String = "ru"
    ) : ForecastData
    
}
