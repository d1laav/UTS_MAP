package com.android.example.uts_map.ui.screen.atlas

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.example.uts_map.model.DiaryEntry
import com.android.example.uts_map.ui.screen.journey.stringToLatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtlasScreen(
    diaryEntries: List<DiaryEntry>,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-6.2, 106.8), 5f)
    }

    // Membuat list lokasi dari DiaryEntry
    val locationList = remember(diaryEntries) {
        diaryEntries.mapNotNull { entry ->
            entry.location?.let {
                val latLng = stringToLatLng(it)
                Pair(latLng, entry.title) // simpan latLng dan judul
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Area") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            // tampilan mark point
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                locationList.forEach { (latLng, title) ->
                    Marker(
                        state = MarkerState(position = latLng),
                        title = title,
                        snippet = "Klik untuk melihat judul",
                        onClick = {
                            Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
                            true  // true untuk menangani klik marker
                        }
                    )
                }
            }
        }
    }
}
