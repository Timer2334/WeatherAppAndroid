package com.example.timer2334_20.presentation.ui.components.drawerSettings

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer2334_20.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDrawerSettings(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerStateSettings: DrawerState
) {
    val scope = rememberCoroutineScope()
    val hasScrolled by remember {
        derivedStateOf { scrollBehavior.state.overlappedFraction < 0.01f }
    }
    scrollBehavior.state.overlappedFraction
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(hasScrolled) {
        animationProgress.animateTo(
            targetValue = if (hasScrolled) 6f else 0f,
            animationSpec = spring<Float>(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        
        )
    }
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Настройки",
                fontSize = 22.sp,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                fontWeight = FontWeight.W500,
                color = colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerStateSettings.close()
                    }
                },
                modifier = Modifier
                    .padding(start = 0.dp)
                    .offset((animationProgress.value).dp)
                    .background(colorScheme.primaryContainer, CircleShape)
            
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_ios),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(4.dp),
                    tint = colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorScheme.surface
            //Color(0xFFF7F8FA)
            ,
            scrolledContainerColor = colorScheme.primaryContainer
            //Color.White
            ,
            navigationIconContentColor = colorScheme.onSurface
            //Color(0xFF262632)
            ,
            titleContentColor = colorScheme.onSurface,
            
            //Color(0xFF262632)
        ),
        scrollBehavior = scrollBehavior
    )
}

