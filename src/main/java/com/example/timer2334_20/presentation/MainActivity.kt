package com.example.timer2334_20.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timer2334_20.domain.model.ThemeConfig
import com.example.timer2334_20.presentation.ui.WeatherViewModel
import com.example.timer2334_20.presentation.ui.components.mainWindow.MainWindow
import com.example.timer2334_20.presentation.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<WeatherViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(applicationContext) as T
            }
        }
    })

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.changeCheck()
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.userData.value?.let { true } == true
        }
        enableEdgeToEdge()
        setContent {
            val text by viewModel.userData.collectAsState()
            AppTheme(
                viewModel.userData.collectAsState().value?.themeConfig
                    ?: ThemeConfig.FOLLOW_SYSTEM
            ) {
                MainWindow(viewModel)
            }
            Column {
                Text(text.toString())
            }
        }
    }
}
