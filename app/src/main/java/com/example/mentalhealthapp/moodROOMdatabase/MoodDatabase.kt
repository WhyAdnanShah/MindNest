package com.example.mentalhealthapp.moodROOMdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoodEntity::class], version = 1)

abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDAO
}

