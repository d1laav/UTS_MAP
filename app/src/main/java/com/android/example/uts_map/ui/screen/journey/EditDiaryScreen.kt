@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.example.uts_map.ui.screen.journey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.example.uts_map.model.DiaryEntry
import kotlinx.coroutines.launch

@Composable
fun EditDiaryScreen(
    entry: DiaryEntry,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf(entry.title) }
    var content by remember { mutableStateOf(entry.content) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(entry.date) },
                navigationIcon = {
                    IconButton(onClick = {
                        entry.title = title
                        entry.content = content
                        onSave()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Catatan disimpan ✅")
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Simpan")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onDelete()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Catatan dihapus 🗑️")
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()
        ) {
            //judul notes
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Edit Judul") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            //isi notes
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Edit Catatan") },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
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
