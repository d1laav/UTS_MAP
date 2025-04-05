package com.android.example.uts_map.ui.screen.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.android.example.uts_map.model.DiaryEntry
import com.android.example.uts_map.ui.component.navbar.BottomNavigationBar
import com.android.example.uts_map.ui.screen.journey.EditDiaryScreen
import com.android.example.uts_map.ui.screen.journey.JourneyScreen
import com.android.example.uts_map.ui.screen.journey.NewEntryScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "journey"


    val diaryList = remember {
        mutableStateListOf<DiaryEntry>(
            DiaryEntry(1, "February 10, 2025", "08:12 am", "Pagi yang Produktif", "Membaca buku pengembangan diri selama 30 menit dan membuat kopi."),
            DiaryEntry(2, "February 10, 2025", "10:00 am", "Menulis Jurnal", "Mencatat pengalaman liburan ke Jogja minggu lalu."),
            DiaryEntry(3, "February 10, 2025", "03:20 pm", "Belanja Mingguan", "Pergi ke supermarket beli sayur, buah, dan kebutuhan rumah."),

            DiaryEntry(4, "February 11, 2025", "09:15 am", "Sarapan Santai", "Sarapan roti bakar dan teh, sambil baca koran online."),
            DiaryEntry(5, "February 11, 2025", "12:30 pm", "Meeting Klien", "Diskusi via Zoom dengan klien luar negeri soal UI redesign."),
            DiaryEntry(6, "February 11, 2025", "04:00 pm", "Ngopi Bareng Teman", "Ketemu Andre, ngobrol panjang soal masa kuliah."),
            DiaryEntry(7, "February 11, 2025", "09:00 pm", "Skripsi Lagi", "Fokus nulis bab 3 dan revisi outline untuk dosen."),

            DiaryEntry(8, "February 12, 2025", "07:00 am", "Jogging Pagi", "Lari 3KM di sekitar kompleks sambil dengar podcast inspiratif."),
            DiaryEntry(9, "February 12, 2025", "01:30 pm", "Belajar Compose", "Eksperimen layout baru pakai Jetpack Compose & ChatGPT."),
            DiaryEntry(10,"February 12, 2025", "05:00 pm", "Nonton Anime", "Episode terbaru dari Jujutsu Kaisen bareng adik."),
            DiaryEntry(11, "February 12, 2025", "08:00 pm", "Proyek Kampus", "Beresin halaman terakhir proyek akhir semester."),

            DiaryEntry(12, "February 13, 2025", "08:00 am", "Sarapan Pancake", "Masak pancake sendiri untuk sarapan, tambah madu 🍯."),
            DiaryEntry(13, "February 13, 2025", "11:45 am", "Diskusi Skripsi", "Zoom call dengan dosen pembimbing, bahas metode penelitian."),
            DiaryEntry(14, "February 13, 2025", "02:00 pm", "Nulis Blog", "Tulis artikel soal self-growth di usia 20-an."),
            DiaryEntry(15, "February 13, 2025", "07:00 pm", "Film Dokumenter", "Nonton dokumenter sosial di Netflix bareng keluarga.")
        )
    }


    println(">> Current route: $currentRoute")

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "journey",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("journey") {
                println(">> Showing Journey Screen")
                JourneyScreen(
                    diaryList = diaryList
                        .sortedWith(compareByDescending<DiaryEntry> { it.date }
                            .thenByDescending { it.time }),
                    onEntryClick = { entry ->
                        navController.navigate("edit_entry/${entry.id}")
                    },
                    onNewEntryClick = {
                        navController.navigate("new_entry")
                    }
                )
            }
            composable("new_entry") {
                NewEntryScreen(
                    onSave = { newEntry ->
                        diaryList.add(0, newEntry)
                        navController.popBackStack()
                    }
                )
            }

            composable("edit_entry/{id}") { backStackEntry ->
                val entryId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                val entry = diaryList.find { it.id == entryId }

                if (entry != null) {
                    EditDiaryScreen(
                        entry = entry,
                        onSave = {
                            navController.popBackStack()
                        },
                        onDelete = {
                            diaryList.removeIf { it.id == entry.id }
                            navController.popBackStack()
                        },
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                } else {
                    Text("Catatan tidak ditemukan")
                }
            }

            composable("calendar") {
                println(">> Showing Calendar Screen")
                Text("Calendar Screen", modifier = Modifier.padding(16.dp))
            }
            composable("media") {
                println(">> Showing Media Screen")
                Text("Media Screen", modifier = Modifier.padding(16.dp))
            }
            composable("atlas") {
                println(">> Showing Atlas Screen")
                Text("Atlas Screen", modifier = Modifier.padding(16.dp))
            }
        }
    }
}



