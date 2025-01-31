package com.example.timer2334_20.presentation.ui.components.drawerMenu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavigationDrawerMenu(
    drawerStateMenu: DrawerState,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize(),
        drawerState = drawerStateMenu,
        drawerContent = {
            drawerContent()
        },
        gesturesEnabled = drawerStateMenu.isOpen,
//scrimColor = Color.DarkGray,
        content = {
            content()
        }
    )
}