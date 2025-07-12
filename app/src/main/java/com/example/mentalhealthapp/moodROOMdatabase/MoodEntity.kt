package com.example.mentalhealthapp.moodROOMdatabase

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "mood_table")
data class MoodEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val mood: String,
    var note: String = "",
    val date: String,
//    val timeStamp: Long
)