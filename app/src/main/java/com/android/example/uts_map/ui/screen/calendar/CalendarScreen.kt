package com.android.example.uts_map.ui.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Halo, user123") },
                actions = {
                    IconButton(onClick = { /* Profile pic */ }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarHeader(
                selectedDate = selectedDate,
                onPreviousMonth = { selectedDate = selectedDate.minusMonths(1) },
                onNextMonth = { selectedDate = selectedDate.plusMonths(1) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CalendarGrid(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(
    selectedDate: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }

        Text(
            text = selectedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)),
            style = MaterialTheme.typography.titleMedium
        )

        IconButton(onClick = onNextMonth) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val startOfMonth = selectedDate.withDayOfMonth(1)
    val endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth())
    val firstDayOfWeek = (startOfMonth.dayOfWeek.value % 7) // Sunday = 0
    val totalDays = firstDayOfWeek + endOfMonth.dayOfMonth
    val weeks = (totalDays + 6) / 7

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header Hari
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tanggal
        for (week in 0 until weeks) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (dayOfWeek in 0..6) {
                    val dayNumber = week * 7 + dayOfWeek - firstDayOfWeek + 1
                    val currentDay = startOfMonth.withDayOfMonth(
                        dayNumber.coerceIn(1, endOfMonth.dayOfMonth)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(
                                when {
                                    dayNumber !in 1..endOfMonth.dayOfMonth -> Color.Transparent
                                    currentDay == today -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    currentDay == selectedDate -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = dayNumber in 1..endOfMonth.dayOfMonth) {
                                onDateSelected(currentDay)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (dayNumber in 1..endOfMonth.dayOfMonth) {
                            Text(
                                text = dayNumber.toString(),
                                style = if (currentDay == selectedDate) {
                                    MaterialTheme.typography.titleMedium
                                } else {
                                    MaterialTheme.typography.bodyMedium
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
