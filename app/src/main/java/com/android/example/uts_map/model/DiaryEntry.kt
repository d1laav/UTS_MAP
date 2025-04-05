package com.android.example.uts_map.model

data class DiaryEntry(
    val id: Int,
    val date: String,
    val time: String,
    var title: String,
    var content: String
)