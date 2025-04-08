package com.android.example.uts_map.ui.screen.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.android.example.uts_map.model.DiaryEntry
import com.android.example.uts_map.ui.component.navbar.BottomNavigationBar
import com.android.example.uts_map.ui.screen.calendar.CalendarScreen
import com.android.example.uts_map.ui.screen.journey.DetailDiaryScreen
import com.android.example.uts_map.ui.screen.journey.EditDiaryScreen
import com.android.example.uts_map.ui.screen.journey.JourneyScreen
import com.android.example.uts_map.ui.screen.journey.NewEntryScreen
import com.android.example.uts_map.ui.screen.media.MediaScreen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.layout.size









@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "journey"
    val idCounter = remember { mutableIntStateOf(1000) }
    val configuration = LocalConfiguration.current
    val tablet = configuration.screenWidthDp >= 600


    val diaryList = remember {
        mutableStateListOf<DiaryEntry>(
            DiaryEntry(
                1,
                "February 10, 2025",
                "08:12 am",
                "Pagi yang Produktif",
                "Membaca buku pengembangan diri selama 30 menit dan membuat kopi."
            ),
            DiaryEntry(
                2,
                "February 10, 2025",
                "10:00 am",
                "Menulis Jurnal",
                "Mencatat pengalaman liburan ke Jogja minggu lalu."
            ),
            DiaryEntry(
                3,
                "February 10, 2025",
                "03:20 pm",
                "Belanja Mingguan",
                "Pergi ke supermarket beli sayur, buah, dan kebutuhan rumah."
            ),

            DiaryEntry(
                4,
                "February 11, 2025",
                "09:15 am",
                "Sarapan Santai",
                "Sarapan roti bakar dan teh, sambil baca koran online."
            ),
            DiaryEntry(
                5,
                "February 11, 2025",
                "12:30 pm",
                "Meeting Klien",
                "Diskusi via Zoom dengan klien luar negeri soal UI redesign."
            ),
            DiaryEntry(
                6,
                "February 11, 2025",
                "04:00 pm",
                "Ngopi Bareng Teman",
                "Ketemu Andre, ngobrol panjang soal masa kuliah."
            ),
            DiaryEntry(
                7,
                "February 11, 2025",
                "09:00 pm",
                "Skripsi Lagi",
                "Fokus nulis bab 3 dan revisi outline untuk dosen."
            ),

            DiaryEntry(
                8,
                "February 12, 2025",
                "07:00 am",
                "Jogging Pagi",
                "Lari 3KM di sekitar kompleks sambil dengar podcast inspiratif."
            ),
            DiaryEntry(
                9,
                "February 12, 2025",
                "01:30 pm",
                "Belajar Compose",
                "Eksperimen layout baru pakai Jetpack Compose & ChatGPT."
            ),
            DiaryEntry(
                10,
                "February 12, 2025",
                "05:00 pm",
                "Nonton Anime",
                "Episode terbaru dari Jujutsu Kaisen bareng adik."
            ),
            DiaryEntry(
                11,
                "February 12, 2025",
                "08:00 pm",
                "Proyek Kampus",
                "Beresin halaman terakhir proyek akhir semester."
            ),

            DiaryEntry(
                12,
                "February 13, 2025",
                "08:00 am",
                "Sarapan Pancake",
                "Masak pancake sendiri untuk sarapan, tambah madu 🍯."
            ),
            DiaryEntry(
                13,
                "February 13, 2025",
                "11:45 am",
                "Diskusi Skripsi",
                "Zoom call dengan dosen pembimbing, bahas metode penelitian."
            ),
            DiaryEntry(
                14,
                "February 13, 2025",
                "02:00 pm",
                "Nulis Blog",
                "Tulis artikel soal self-growth di usia 20-an."
            ),
            DiaryEntry(
                15,
                "February 13, 2025",
                "07:00 pm",
                "Film Dokumenter",
                "Nonton dokumenter sosial di Netflix bareng keluarga."
            )
        )
    }


    println(">> Current route: $currentRoute")

    Scaffold(
        bottomBar = {
            if (!tablet) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        Row(modifier = Modifier.padding(innerPadding)) {
            if (tablet) {
                NavigationRail {
                    NavigationRailItem(
                        selected = currentRoute == "journey",
                        onClick = { navController.navigate("journey") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = "Journey",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Journey") },
                        alwaysShowLabel = true
                    )
                    NavigationRailItem(
                        selected = currentRoute == "calendar",
                        onClick = { navController.navigate("calendar") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Calendar",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Calendar") },
                        alwaysShowLabel = true
                    )
                    NavigationRailItem(
                        selected = currentRoute == "media",
                        onClick = { navController.navigate("media") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Media",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Media") },
                        alwaysShowLabel = true
                    )
                    NavigationRailItem(
                        selected = currentRoute == "atlas",
                        onClick = { navController.navigate("atlas") },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Atlas",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Atlas") },
                        alwaysShowLabel = true
                    )
                }
            }

            Box(modifier = Modifier.weight(1f)) {
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
                                navController.navigate("detail_entry/${entry.id}")
                            },
                            onNewEntryClick = {
                                navController.navigate("new_entry")
                            }
                        )
                    }

                    composable("new_entry") {
                        NewEntryScreen(
                            onSave = { newEntry ->
                                newEntry.id = idCounter.value++
                                diaryList.add(newEntry)
                                navController.popBackStack()
                            },
                            navController = navController
                        )
                    }

                    composable(
                        route = "detail_entry/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        val entry = diaryList.find { it.id == id }

                        if (entry != null) {
                            val currentIndex = diaryList.indexOf(entry)

                            DetailDiaryScreen(
                                entry = entry,
                                onBack = { navController.popBackStack() },
                                onEditClick = {
                                    navController.navigate("edit_entry/${entry.id}")
                                },
                                onPrevClick = {
                                    val prev = diaryList.getOrNull(currentIndex - 1)
                                    if (prev != null) {
                                        navController.navigate("detail_entry/${prev.id}") {
                                            popUpTo("detail_entry/$id") { inclusive = true }
                                        }
                                    }
                                },
                                onNextClick = {
                                    val next = diaryList.getOrNull(currentIndex + 1)
                                    if (next != null) {
                                        navController.navigate("detail_entry/${next.id}") {
                                            popUpTo("detail_entry/$id") { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                    }
                    composable("edit_entry/{id}") { backStackEntry ->
                        val entryId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        val entry = diaryList.find { it.id == entryId }

                        if (entry == null) {
                            LaunchedEffect(Unit) {
                                navController.popBackStack()
                            }
                        } else {
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
                                },
                                navController = navController
                            )
                        }
                    }


                    composable("calendar") {
                        println(">> Showing Calendar Screen")
                        CalendarScreen(
                            diaryList = diaryList,
                            onEntryClick = { entry ->
                                navController.navigate("detail_entry/${entry.id}")
                            },
                            navController = navController
                        )
                    }
                    composable("media") {
                        println(">> Showing Media Screen")
                        MediaScreen(
                            diaryList = diaryList,
                            onProfileClick = {
                                // ** profile di click **
                            }
                        )
                    }
                    composable("atlas") {
                        println(">> Showing Atlas Screen")
                        Text("Atlas Screen", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }

        @Composable
        fun isTablet(): Boolean {
            val configuration = LocalConfiguration.current
            val screenWidthDp = configuration.screenWidthDp
            return screenWidthDp >= 600 // threshold tablet umumnya di 600dp
        }
    }
}
