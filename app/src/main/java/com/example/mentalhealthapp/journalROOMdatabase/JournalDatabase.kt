package com.example.mentalhealthapp.journalROOMdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JournalEntity::class], version = 2)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDAO
}