package com.example.timer2334_20.presentation.ui.components.mainWindow

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.timer2334_20.presentation.ui.WeatherViewModel
import com.example.timer2334_20.presentation.ui.components.drawerMenu.DrawerMenu
import com.example.timer2334_20.presentation.ui.components.drawerMenu.NavigationDrawerMenu
import com.example.timer2334_20.presentation.ui.components.drawerSettings.DrawerSettings
import com.example.timer2334_20.presentation.ui.components.drawerSettings.NavigationDrawerSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainWindow(
    viewModel: WeatherViewModel
) {
    val drawerStateMenu = remember { DrawerState(DrawerValue.Closed) }
    val drawerStateSettings = remember { DrawerState(DrawerValue.Closed) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    NavigationDrawerMenu(
        drawerStateMenu = drawerStateMenu,
        drawerContent = {
            DrawerMenu(drawerStateMenu)
        },
        content = {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBarMainWindow(
                        viewModel = viewModel,
                        scrollBehavior = scrollBehavior,
                        drawerStateMenu = drawerStateMenu,
                        drawerStateSettings = drawerStateSettings
                    )
                }
            ) { innerPadding ->
                NavigationDrawerSettings(
                    drawerContent = {
                        DrawerSettings(drawerStateSettings, viewModel)
                    },
                    content = {
                        Content(viewModel, innerPadding)
                    },
                    drawerStateSettings = drawerStateSettings
                )
            }
        }
    )
}