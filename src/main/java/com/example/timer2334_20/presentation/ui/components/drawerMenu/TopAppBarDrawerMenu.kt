package com.example.timer2334_20.presentation.ui.components.drawerMenu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.timer2334_20.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDrawerMenu(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerStateMenu: DrawerState,
    onClickChange: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        ),
        title = {
            Text(
                text = "   Избранное",
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerStateMenu.close()
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_ios),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onClickChange(true)
            }) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.8f),
                    tint = Color.White
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}