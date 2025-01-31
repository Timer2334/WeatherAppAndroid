package com.example.timer2334_20.presentation.ui.components.mainWindow.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.timer2334_20.R
import com.example.timer2334_20.data.model.WeatherResponse

@Composable
fun TopDisplayWeather(
    weatherResponse: WeatherResponse?,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colorScheme.primaryContainer,
                        colorScheme.secondaryContainer,
                        colorScheme.tertiaryContainer
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.06f)
            )
            if (weatherResponse != null) WeatherDisplay(weatherResponse) else LoadingPage()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun WeatherDisplay(data: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.88f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${if (data.main.temp >= 0.0) "+" else ""}${data.main.temp}°",
                fontSize = 90.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif,
            )
            GlideImage(
                model = "https://openweathermap.org/img/wn/${data.weather[0].icon}@4x.png",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(105.dp)
                    .offset((-10).dp)
                    .graphicsLayer(scaleX = 1.5f, scaleY = 1.5f),
            )
        }
        Text(
            text = data.weather[0].description,
            fontSize = 28.sp,
            color = Color.White,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.alpha(0.7f)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        when (data.main.humidity) {
                            in 0..33 -> R.drawable.humidity_low
                            in 34..66 -> R.drawable.humidity_mid
                            in 67..100 -> R.drawable.humidity_high
                            else -> R.drawable.humidity_mid
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
                Text(
                    text = " Влажность ${data.main.humidity}%",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.bootstrap_wind),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
                Text(
                    text = " Ветер ${data.wind.speed} м/c",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                )
            }
        }
    }
}

@Composable
private fun LoadingPage() {
    val compositionLoading by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(
            "loading.json"
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
    ) {
        LottieAnimation(
            composition = compositionLoading,
            iterations = LottieConstants.IterateForever,
            speed = 1.15f,
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}
