package com.android.example.uts_map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.example.uts_map.ui.screen.main.MainScreen
import com.android.example.uts_map.ui.theme.UTS_MAPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTS_MAPTheme {
                MyApp()
            }
        }
    }
}
