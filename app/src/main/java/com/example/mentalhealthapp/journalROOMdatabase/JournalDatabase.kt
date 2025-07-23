package com.example.mentalhealthapp.journalROOMdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [JournalEntity::class], version = 4)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDAO
}