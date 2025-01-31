package com.example.timer2334_20.presentation.ui.components.drawerSettings

import android.text.TextPaint
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
private fun SegmentedButtonsPreview() {
    var index by remember { mutableIntStateOf(0) }
    SegmentedButtons(
        list = listOf("ss","aa"),
        index,
        onSelectionChange = { index = it },
        colorConfig = ColorConfig(
            unselectedTextColor = Color(0xFF898C92),
            selectedTextColor = Color.White,
            indicatorColor = Color(0xFF242430),
            outlineColor = Color(0xFF898C92)
        ),
        sizeConfig = SizeConfig(
            fontSize = 43.sp,
            contentPadding = 11.dp,
            itemPadding = 6.dp,
            itemSpacing = 24f,
            borderWidth = 1.dp,
            spacerWidth = 20.dp
        )
    )
}

@Composable
fun SegmentedButtons(
    list: List<String>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
    animationSpec: AnimationSpec<Dp> = spring<Dp>(),
    sizeConfig: SizeConfig = SizeConfig(),
    colorConfig: ColorConfig
) {
    val totalPadding = remember { mutableStateMapOf<Int, Dp>() }
    var isDrawingEnabled by remember { mutableStateOf(true) }
    val sizeWidth = remember { mutableStateMapOf<Int, Dp>() }
    var sizeHeight by remember { mutableFloatStateOf(0f) }
    val scrollState = rememberScrollState()
    var valueTotalPadding = 0f
    
    // Анимация смещения по X
    val animatedOffsetX by animateDpAsState(
        targetValue = totalPadding[selectedIndex] ?: (0).dp,
        animationSpec = if (totalPadding[selectedIndex] == null) {
            snap()
        } else {
            animationSpec
        },
        label = ""
    )
    
    // Анимация ширины фона
    val animatedWidth by animateDpAsState(
        targetValue = sizeWidth[selectedIndex] ?: sizeHeight.dp,
        animationSpec = animationSpec,
        label = ""
    )
    
    val availableTextWidthInDp =
        LocalConfiguration.current.screenWidthDp.dp - sizeConfig.spacerWidth * 2 - sizeConfig.contentPadding * 2 - (sizeConfig.itemSpacing * (list.size - 1).toFloat()).dp - sizeConfig.itemPadding * list.size * 2
    val availableItemWidth =
        (LocalConfiguration.current.screenWidthDp.dp - sizeConfig.spacerWidth * 2 - (sizeConfig.itemSpacing * (list.size - 1).toFloat()).dp - sizeConfig.contentPadding * 2) / list.size
    val listTextWidthsInDp = getTextWidthsInDp(list, sizeConfig.fontSize)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Spacer(
            modifier = Modifier
                .width(sizeConfig.spacerWidth)
        )
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                Modifier
                    .height(sizeHeight.dp)
                    .width(animatedWidth)
                    .offset(animatedOffsetX + sizeConfig.contentPadding)
                    .clip(CircleShape)
                    .background(colorConfig.indicatorColor),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = sizeConfig.borderWidth,
                        color = colorConfig.outlineColor,
                        shape = CircleShape
                    )
                    .padding(sizeConfig.contentPadding),
                horizontalArrangement = Arrangement.spacedBy(sizeConfig.itemSpacing.dp)
            ) {
                list.forEachIndexed { index, value ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onSelectionChange(index)
                                
                            }
                            .drawBehind {
                                if (isDrawingEnabled) {
                                    sizeWidth[index] = (size.width / density).dp
                                    sizeHeight = size.height / density
                                    totalPadding[index] = valueTotalPadding.dp
                                    valueTotalPadding += (size.width / density) + sizeConfig.itemSpacing
                                }
                                if (index == list.size - 1) isDrawingEnabled = false
                            }
                            .width(
                                if (listTextWidthsInDp[index] < availableTextWidthInDp / list.size) {
                                    availableItemWidth
                                } else {
                                    Dp.Unspecified
                                }
                            )
                            .padding(sizeConfig.itemPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = value,
                            maxLines = 1,
                            fontSize = sizeConfig.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = (if (index == selectedIndex) {
                                colorConfig.selectedTextColor
                            } else {
                                colorConfig.unselectedTextColor
                            })
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .width(sizeConfig.spacerWidth)
        )
    }
}

@Composable
private fun getTextWidthsInDp(
    strings: List<String>,
    fontSize: TextUnit
): List<Dp> {
    val density = LocalDensity.current
    val textWidthsInDp = remember(strings, fontSize) {
        val fontSizePx = with(density) { fontSize.toPx() }
        val textPaint = TextPaint().apply { textSize = fontSizePx }
        
        strings.map { string ->
            val textWidthPx = textPaint.measureText(string)
            with(density) { textWidthPx.toDp() }
        }
    }
    return textWidthsInDp
}

data class SizeConfig(
    val fontSize: TextUnit = 16.sp,
    val contentPadding: Dp = 8.dp,
    val itemPadding: Dp = 16.dp,
    val itemSpacing: Float = 10f,
    val borderWidth: Dp = 1.dp,
    val spacerWidth: Dp = 16.dp
)

data class ColorConfig(
    val unselectedTextColor: Color,
    val selectedTextColor: Color,
    val indicatorColor: Color,
    val outlineColor: Color
)