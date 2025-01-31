package com.example.timer2334_20.presentation.ui.components.mainWindow.content

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.timer2334_20.domain.model.DailyForecasts
import com.example.timer2334_20.domain.model.ForecastForUI

@Composable
fun DisplayForecast(
    dailyForecasts: DailyForecasts<ForecastForUI>?
    ) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp),
    ) {
        itemsIndexed(
            dailyForecasts?.list ?: listOf()
        ) { index, item ->
            WeatherForecastItem(index, item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun WeatherForecastItem(
    index: Int,
    data: ForecastForUI
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                Log.i("t","aaaaaaa")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.secondaryContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                GlideImage(
                    model = "https://openweathermap.org/img/wn/${data.icon}@4x.png",
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .graphicsLayer(scaleX = 1.42f, scaleY = 1.42f),
                )
                Text(
                    text = "${data.temp}°C",
                    color = colorScheme.onSecondaryContainer,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 29.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                )
            }
            Text(
                text = data.time.weekdayOrRelative,
                color = colorScheme.onSecondaryContainer,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 1.dp)
            )
        }
    }
}

