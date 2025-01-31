package com.example.timer2334_20.presentation.ui.components.drawerSettings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer2334_20.R
import com.example.timer2334_20.data.DARKTHEMECONFIG_KEY
import com.example.timer2334_20.data.TEMPERATURE_KEY
import com.example.timer2334_20.data.WINDSPEED_KEY
import com.example.timer2334_20.domain.model.Temperature
import com.example.timer2334_20.domain.model.ThemeConfig
import com.example.timer2334_20.domain.model.WindSpeed
import com.example.timer2334_20.presentation.ui.WeatherViewModel
import com.example.timer2334_20.presentation.ui.theme.Shapes


@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Composable
fun DrawerSettings(
    drawerStateSettings: DrawerState,
    viewModel: WeatherViewModel
) {
    var bottomSheetIsOpen by rememberSaveable { mutableStateOf(false) }
    var selectedContent by remember { mutableStateOf<@Composable () -> Unit>({}) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    val padding = 25.dp
    
    val modifier = Modifier
        .fillMaxWidth()
        .clip(Shapes.medium)
        .background(colorScheme.primaryContainer)
    
    val sizeConfig = SizeConfig(
        fontSize = 30.sp,
        contentPadding = 6.dp,
        itemPadding = 10.dp,
        itemSpacing = 0f,
        borderWidth = 2.dp,
        spacerWidth = 25.dp
    )
    
    val colorConfig = ColorConfig(
        unselectedTextColor = colorScheme.surfaceVariant,
        selectedTextColor = colorScheme.onPrimaryContainer,
        indicatorColor = colorScheme.surfaceBright,
        outlineColor = colorScheme.outline
    )
    
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopBarDrawerSettings(scrollBehavior, drawerStateSettings)
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    animateColorAsState(
                        targetValue = colorScheme.surface,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        label = ""
                    ).value
                )
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(padding - 10.dp)
        ) {
            ConfigureWeatherSettings(
                modifier = modifier,
                viewModel = viewModel,
                sizeConfig = sizeConfig,
                colorConfig = colorConfig,
                padding = padding
            )
            ChangeThemeColor(
                modifier = modifier,
                viewModel = viewModel,
                sizeConfig = sizeConfig,
                colorConfig = colorConfig,
                padding = padding
            )
            Location(modifier = modifier,
                viewModel = viewModel,
                padding = padding,
                bottomSheetIsOpen = { bottomSheetIsOpen = it },
                selectedContent = { selectedContent = it })
        }
        BottomSheet(bottomSheetIsOpen = bottomSheetIsOpen,
            containerColor = colorScheme.primaryContainer,
            onDismissRequest = { bottomSheetIsOpen = false },
            content = { selectedContent() })
    }
}

@Stable
@Composable
private fun IconWithTextRow(
    painterResourceId: Int,
    text: String,
    sizeIcon: Dp = 40.dp,
    fontSize: TextUnit = 24.sp,
    tintIcon: Color = colorScheme.onSurfaceVariant,
    hasBackground: Boolean = false,
    iconIsVisible: Boolean = true
) {
    val padding = 25.dp
    val paddingInIcon = 5.dp
    Row(
        modifier = Modifier.padding(padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(painterResourceId),
            tint = if (iconIsVisible) tintIcon else Color.Transparent,
            contentDescription = null,
            modifier = Modifier
                .let {
                    if (hasBackground) {
                        it
                            .clip(Shapes.small)
                            .background(colorScheme.surfaceDim)
                            .padding(paddingInIcon)
                            .size(60.dp)
                    } else {
                        it
                    }
                }
                .size(sizeIcon)
        )
        Text(
            modifier = Modifier
                .basicMarquee(
                    spacing = MarqueeSpacing.fractionOfContainer(1f / 10f),
                    repeatDelayMillis = 1_100
                )
                .padding(start = padding - 5.dp, end = padding - 5.dp),
            text = text,
            maxLines = 1,
            fontSize = fontSize,
            color = colorScheme.onSurface
        )
    }
}

@Stable
@Composable
private fun ConfigureWeatherSettings(
    modifier: Modifier,
    viewModel: WeatherViewModel,
    sizeConfig: SizeConfig,
    colorConfig: ColorConfig,
    padding: Dp
) {
    Column(
        modifier = modifier
    ) {
        IconWithTextRow(
            R.drawable.bootstrap_thermometer_half,
            "Температура"
        )
        SegmentedButtons(
            listOf("°C", "℉"),
            when (viewModel.userData.collectAsState().value?.temperature) {
                Temperature.METRIC -> 0
                Temperature.IMPERIAL -> 1
                else -> 0
            },
            {
                viewModel.changeData(
                    TEMPERATURE_KEY, when (it) {
                        0 -> Temperature.METRIC.toString()
                        1 -> Temperature.IMPERIAL.toString()
                        else -> Temperature.METRIC.toString()
                    }
                )
                
            },
            sizeConfig = sizeConfig,
            colorConfig = colorConfig
        )
        Spacer(modifier = Modifier.height(padding))
        IconWithTextRow(
            R.drawable.lucide_wind, "Сила ветра"
        )
        SegmentedButtons(
            WindSpeed.entries.map { it.value },
            when (viewModel.userData.collectAsState().value?.windSpeed) {
                WindSpeed.METERS_PER_SECOND -> 0
                WindSpeed.KILOMETERS_PER_HOUR -> 1
                WindSpeed.MILES_PER_HOUR -> 2
                WindSpeed.KNOTS -> 3
                else -> 0
            },
            {
                viewModel.changeData(
                    WINDSPEED_KEY, when (it) {
                        0 -> WindSpeed.METERS_PER_SECOND.name
                        1 -> WindSpeed.KILOMETERS_PER_HOUR.name
                        2 -> WindSpeed.MILES_PER_HOUR.name
                        3 -> WindSpeed.KNOTS.name
                        else -> WindSpeed.METERS_PER_SECOND.name
                    }
                )
            },
            sizeConfig = sizeConfig,
            colorConfig = colorConfig
        )
        Spacer(modifier = Modifier.height(padding))
        IconWithTextRow(
            R.drawable.pressure, "Давление"
        )
        SegmentedButtons(
            listOf("dd", "ss"),
            1,
            { it },
            sizeConfig = sizeConfig,
            colorConfig = colorConfig
        )
        Spacer(modifier = Modifier.height(padding + 15.dp))
    }
}

@Stable
@Composable
private fun ChangeThemeColor(
    modifier: Modifier,
    viewModel: WeatherViewModel,
    sizeConfig: SizeConfig,
    colorConfig: ColorConfig,
    padding: Dp
) {
    Column(
        modifier = modifier
    ) {
        Column {
            Text(
                text = "Тема оформления",
                color = colorScheme.onSurface,
                fontWeight = FontWeight.W600,
                fontSize = 30.sp,
                modifier = Modifier.padding(padding)
            )
            SegmentedButtons(
                listOf("Системная", "Светлая", "Тёмная"),
                when (viewModel.userData.collectAsState().value?.themeConfig) {
                    ThemeConfig.FOLLOW_SYSTEM -> 0
                    ThemeConfig.LIGHT -> 1
                    ThemeConfig.DARK -> 2
                    else -> 0
                },
                {
                    viewModel.changeData(
                        DARKTHEMECONFIG_KEY, when (it) {
                            0 -> ThemeConfig.FOLLOW_SYSTEM.toString()
                            1 -> ThemeConfig.LIGHT.toString()
                            2 -> ThemeConfig.DARK.toString()
                            else -> ThemeConfig.FOLLOW_SYSTEM.toString()
                        }
                    )
                },
                sizeConfig = sizeConfig,
                colorConfig = colorConfig
            )
            Spacer(modifier = Modifier.height(padding + 15.dp))
        }
    }
}

@Stable
@Composable
fun CityNameWithArrow(
    padding: Dp,
    viewModel: WeatherViewModel
) {
    Row(
        modifier = Modifier.padding(padding)
    ) {
        Text(
            modifier = Modifier.padding(end = padding - 5.dp),
            text = viewModel.userData.collectAsState().value?.cityName
                ?: "",
            maxLines = 1,
            fontSize = 23.sp,
            color = colorScheme.onSurfaceVariant
        )
        Icon(
            painter = painterResource(R.drawable.arrow_forward_ios),
            tint = colorScheme.onSurfaceVariant,
            contentDescription = null
        )
    }
}

@Stable
@Composable
private fun Location(
    modifier: Modifier,
    viewModel: WeatherViewModel,
    padding: Dp,
    bottomSheetIsOpen: (Boolean) -> Unit,
    selectedContent: (content: @Composable () -> Unit) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                bottomSheetIsOpen(true)
                selectedContent {
                    Text(
                        text = "Местоположение",
                        fontSize = 50.sp,
                        fontWeight = FontWeight.W800,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(padding))
                    var selectedCurrentLocation by remember {
                        mutableStateOf(
                            false
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCurrentLocation = false
                            }
                    ) {
                        IconWithTextRow(
                            painterResourceId = R.drawable.check,
                            text = "Текущее место",
                            sizeIcon = 50.dp,
                            fontSize = 25.sp,
                            tintIcon = colorScheme.onSurface,
                            iconIsVisible = !selectedCurrentLocation
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 90.dp, end = 30.dp),
                        thickness = 2.dp,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCurrentLocation = true
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconWithTextRow(
                            painterResourceId = R.drawable.check,
                            text = "Выбрать локацию",
                            sizeIcon = 50.dp,
                            fontSize = 25.sp,
                            tintIcon = colorScheme.onSurface,
                            iconIsVisible = selectedCurrentLocation
                        )
                        CityNameWithArrow(
                            padding,
                            viewModel
                        )
                    }
                    Spacer(modifier = Modifier.height(200.dp))
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            IconWithTextRow(
                hasBackground = true,
                painterResourceId = R.drawable.location,
                text = "Местоположение",
                fontSize = 22.sp,
                tintIcon = colorScheme.onSurface
            )
        }
        CityNameWithArrow(
            padding,
            viewModel
        )
    }
}