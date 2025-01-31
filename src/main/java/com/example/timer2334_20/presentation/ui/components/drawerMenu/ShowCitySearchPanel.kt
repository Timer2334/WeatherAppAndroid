package com.example.timer2334_20.presentation.ui.components.drawerMenu

import androidx.activity.compose.PredictiveBackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope

@Preview
@Composable
private fun ShowCitySearchPanelPreview() {
    var x = remember { mutableStateOf(false) }
    Box() {
        Button(
            onClick = {
                x.value = true
            }
        ) { }
        ShowCitySearchPanel(
            x.value
        ) { x.value = it }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCitySearchPanel(
    onClick: Boolean,
    onClickChange: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val animationProgress = remember { Animatable(if (onClick) 1f else 0f) }
    val mutatorMutex = remember { MutatorMutex() }
    val animationSpec: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
    
    LaunchedEffect(onClick) {
        val targetValue = if (onClick) 1f else 0f
        if (animationProgress.value != targetValue) {
            animationProgress.animateTo(targetValue, animationSpec)
        }
    }
    
    PredictiveBackHandler(enabled = onClick) { progress ->
        coroutineScope {
            mutatorMutex.mutate {
                try {
                    progress.collect { backEvent ->
                        val interpolatedProgress =
                            CubicBezierEasing(0.1f, 0.1f, 0f, 1f)
                                .transform(backEvent.progress)
                        animationProgress.snapTo(targetValue = 1 - interpolatedProgress)
                    }
                    onClickChange(false)
                } catch (_: CancellationException) {
                    animationProgress.animateTo(
                        1f,
                        animationSpec = animationSpec
                    )
                }
            }
        }
    }
    if (animationProgress.value.toFloat() != 0.0f) {
        if (animationProgress.value.toFloat() == 1.0f) {
            focusRequester.requestFocus()
        }
        Surface {
            Scaffold(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(animationProgress.value),
                topBar = {
                    TopBar(focusRequester = focusRequester) {
                        onClickChange(
                            false
                        )
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.Green)
                ) {
                
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    focusRequester: FocusRequester,
    onClickChange: (Boolean) -> Unit
) {
    TopAppBar(
        expandedHeight = TopAppBarDefaults.TopAppBarExpandedHeight + 15.dp,
//        navigationIcon = {
//            IconButton(onClick = {
//                onClickChange(false)
//            }) {
//                Icon(
//                    painter = rememberVectorPainter(image = Arrow_back),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize(0.9f),
//                    tint = Color.Black
//                )
//            }
//        },
        title = {
            var text by rememberSaveable { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.07f))
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 16.dp,
                        focusedElevation = 16.dp,
                        hoveredElevation = 16.dp
                    ), // Высота тени
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        textStyle = TextStyle(
                            fontSize = 28.sp,
                            shadow = Shadow(
                                color = Color(0xFFF0F8FF),
                                blurRadius = 1f
                            ),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.92f)
                            .focusRequester(focusRequester),
                        placeholder = {
                            Text(
                                text = "Найти город",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        singleLine = true,
                        trailingIcon = {
                            if (text != "") {
                                IconButton(
                                    onClick = { text = "" },
                                    modifier = Modifier.scale(1.4f)
                                ) {
                                    Icon(
                                        Icons.Filled.Clear,
                                        contentDescription = "Clear text"
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    )
}