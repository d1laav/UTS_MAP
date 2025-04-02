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
    val currentRoute = currentBackStack?.destination?.route ?: BottomNavItem.Journey.route

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
            startDestination = BottomNavItem.Journey.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Journey.route) {
                Text("Journey Screen", modifier = Modifier.padding(16.dp))
            }
            composable(BottomNavItem.Calendar.route) {
                Text("Calendar Screen", modifier = Modifier.padding(16.dp))
            }
            composable(BottomNavItem.Media.route) {
                Text("Media Screen", modifier = Modifier.padding(16.dp))
            }
            composable(BottomNavItem.Atlas.route) {
                Text("Atlas Screen", modifier = Modifier.padding(16.dp))
            }
        }
    }
}



