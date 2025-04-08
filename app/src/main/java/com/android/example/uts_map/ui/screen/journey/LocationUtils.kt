package com.android.example.uts_map.ui.screen.journey

import android.content.Context
import android.location.Geocoder
import java.util.Locale

fun getReadableLocation(location: String, context: Context): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val parts = location.split(",")
        val lat = parts[0].toDoubleOrNull()
        val lon = parts[1].toDoubleOrNull()

        if (lat != null && lon != null) {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses?.firstOrNull()?.getAddressLine(0) ?: location
        } else {
            location
        }
    } catch (e: Exception) {
        location // fallback kalau error
    }
}
