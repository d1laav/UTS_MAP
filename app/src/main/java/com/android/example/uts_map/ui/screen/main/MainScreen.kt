package com.android.example.uts_map.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.android.example.uts_map.ui.component.navbar.BottomNavItem
import com.android.example.uts_map.ui.component.navbar.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route ?: "journey"

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
                Text("Journey Screen", modifier = Modifier.padding(16.dp))
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



