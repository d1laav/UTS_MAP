package com.android.example.uts_map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.android.example.uts_map.navigation.AuthNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {
    MaterialTheme {
        AuthNavGraph()
    }
}