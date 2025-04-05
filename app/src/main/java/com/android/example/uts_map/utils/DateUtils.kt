package com.android.example.uts_map.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
    return LocalDate.now().format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTimeString(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
    return LocalTime.now().format(formatter)
}