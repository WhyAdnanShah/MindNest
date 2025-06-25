package com.example.mentalhealthapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_table")
class MoodEntity(mood: String, note: String, date: String) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    val mood: String = ""
    val note: String = ""
    val date: String = ""
}