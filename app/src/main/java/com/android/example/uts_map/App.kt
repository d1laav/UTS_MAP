package com.android.example.uts_map

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.android.example.uts_map.navigation.AuthNavGraph

@Composable
fun MyApp() {
    MaterialTheme {
        AuthNavGraph()
    }
}