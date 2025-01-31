package com.example.timer2334_20.presentation.ui.components.mainWindow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.timer2334_20.R
import com.example.timer2334_20.presentation.ui.WeatherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMainWindow(
    viewModel: WeatherViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerStateMenu: DrawerState,
    drawerStateSettings: DrawerState
) {
    val scope = rememberCoroutineScope()
    val weatherResponse by viewModel.weatherState.collectAsState()
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black
        ),
        title = {
            Text(
                text = if (weatherResponse != null) weatherResponse!!.name else "",
                fontSize = 35.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerStateMenu.open()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.menu),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.9f),
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerStateSettings.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Sharp.Settings,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.85f),
                    tint = Color.White
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}