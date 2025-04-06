package com.android.example.uts_map.model

data class DiaryEntry(
    var id: Int,
    val date: String,
    val time: String,
    var title: String,
    var content: String,
    var imageUri: String? = null,
    var location: String? = null
)
