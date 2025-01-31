package com.example.timer2334_20.domain.model

data class DailyForecasts<T>(
    val list: MutableList<T> = ArrayList<T>(),
    val today: MutableList<T> = ArrayList<T>(),
    val secondDay: MutableList<T> = ArrayList<T>(),
    val thirdDay: MutableList<T> = ArrayList<T>(),
    val fourthDay: MutableList<T> = ArrayList<T>(),
    val fifthDay: MutableList<T> = ArrayList<T>(),
    val sixthDay: MutableList<T> = ArrayList<T>()
)
