package com.android.example.uts_map.ui.screen.journey

import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.example.uts_map.model.DiaryEntry
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapPickerScreen(
    entry: DiaryEntry?,
    onLocationSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    val initialLocation = remember {
        entry?.location?.split(",")?.takeIf { it.size == 2 }?.let {
            LatLng(it[0].toDoubleOrNull() ?: -6.2, it[1].toDoubleOrNull() ?: 106.8)
        } ?: LatLng(-6.2, 106.8)
    }

    var selectedLatLng by remember { mutableStateOf(initialLocation) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, 10f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Lokasi") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                // cari alamat (search)
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Cari alamat...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            val geocoder = Geocoder(context)
                            val results = geocoder.getFromLocationName(searchQuery, 1)
                            if (!results.isNullOrEmpty()) {
                                val result = results[0]
                                val newLatLng = LatLng(result.latitude, result.longitude)
                                selectedLatLng = newLatLng
                                cameraPositionState.move(
                                    CameraUpdateFactory.newLatLngZoom(newLatLng, 15f)
                                )
                            }
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Cari Lokasi")
                        }
                    }
                )

                // google map
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { selectedLatLng = it }
                ) {
                    Marker(
                        state = MarkerState(position = selectedLatLng),
                        title = "Lokasi Dipilih",
                        snippet = "Klik Simpan Lokasi"
                    )
                }

                // save nya
                Button(
                    onClick = {
                        val locationString = "${selectedLatLng.latitude},${selectedLatLng.longitude}"
                        onLocationSelected(locationString)
                        onBack()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text("Simpan Lokasi")
                }
            }
        }
    }
}