package com.android.example.uts_map.ui.screen.journey

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.example.uts_map.model.DiaryEntry
import com.android.example.uts_map.utils.getCurrentTimeString
import com.android.example.uts_map.utils.getTodayDateString
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewEntryScreen(
    onSave: (DiaryEntry) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(getTodayDateString()) }

    val time = remember { getCurrentTimeString() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedDate) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (title.isNotBlank() || content.isNotBlank()) {
                            val newEntry = DiaryEntry(
                                id = Random.nextInt(),
                                date = selectedDate,
                                time = time,
                                title = title,
                                content = content
                            )
                            onSave(newEntry)
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Simpan")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Delete */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // select date
            DateSelector(selectedDate) { selectedDate = it }

            Spacer(modifier = Modifier.height(12.dp))

            // judul notes
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul catatan") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // isi notes
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Isi catatan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // geotag & media button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* TODO: Open Media Picker */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Image, contentDescription = "Media")
                    Spacer(Modifier.width(8.dp))
                    Text("Media")
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = { /* TODO: Open Geotag */ },
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