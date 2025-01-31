package com.example.timer2334_20.presentation.ui.components.drawerSettings

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@Composable
fun NavigationDrawerSettings(
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
    drawerStateSettings: DrawerState
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val offsetX by animateFloatAsState(
        targetValue = if (drawerStateSettings.isOpen) 0f else screenWidth,
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow
        ),
        label = ""
    )
    content()
    if (offsetX != screenWidth) {
        Surface(
            Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(offsetX.roundToInt(), 0)
                }
                .zIndex(1.1f),
        ) {
            drawerContent()
        }
    }
}