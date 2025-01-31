package com.example.timer2334_20.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.timer2334_20.domain.model.ThemeConfig

private val LightColorScheme = lightColorScheme(
    onSurface = deepCharcoal,
    surface = white97,
    surfaceBright = deepCharcoal,
    primaryContainer = Color.White,
    onPrimaryContainer = Color.White,
    onSurfaceVariant = silverBlue,
    surfaceVariant = stoneGray,
    surfaceDim = softIceGray,
    outline = lightGray
)

private val DarkColorScheme = darkColorScheme(
    onSurface = white90,
    surfaceBright = charcoalGrey,
    surface = midnightBlack,
    primaryContainer = midnightBlue,
    onPrimaryContainer = Color.White,
    onSurfaceVariant = lightGrey,
    surfaceVariant = lightGrey,
    surfaceDim = darkSlate,
    outline = charcoalGrey
)

@Composable
fun AppTheme(
    themeConfig: ThemeConfig,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeConfig) {
        ThemeConfig.DARK -> true
        ThemeConfig.LIGHT -> false
        ThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }
    
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

//val colorScheme = when {
//    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//        val context = LocalContext.current
//        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
//            context
//        )
//    }
//    darkTheme -> DarkColorScheme
//    else -> LightColorScheme
//}
