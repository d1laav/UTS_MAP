@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.example.uts_map.ui.screen.journey

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.example.uts_map.model.DiaryEntry
import com.android.example.uts_map.utils.getCurrentTimeString
import com.android.example.uts_map.utils.getTodayDateString
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewEntryScreen(
    onSave: (DiaryEntry) -> Unit,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(getTodayDateString()) }
    val time = remember { getCurrentTimeString() }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedDate) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (title.isNotBlank() || content.isNotBlank()) {
                            val newEntry = DiaryEntry(
                                id = Random.nextInt(1000, 9999), // 🔐 Hindari id duplikat
                                date = selectedDate,
                                time = time,
                                title = title,
                                content = content,
                                imageUri = imageUri?.toString(),
                                location = null
                            )
                            onSave(newEntry)
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Simpan")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        title = ""
                        content = ""
                        imageUri = null
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Reset")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Tampilkan gambar jika ada
            imageUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 📅 Tanggal
            DateSelector(selectedDate) { selectedDate = it }

            Spacer(modifier = Modifier.height(12.dp))

            // 📝 Judul
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul catatan") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 📄 Isi catatan
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Isi catatan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 📷 Tombol Media & Geotag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Image, contentDescription = "Media")
                    Spacer(Modifier.width(8.dp))
                    Text("Media")
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = {
                        navController.navigate("map_picker")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Place, contentDescription = "Geotag")
                    Spacer(Modifier.width(8.dp))
                    Text("Geotag")
                }
            }
        }
    }
}
