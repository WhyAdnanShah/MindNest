package com.example.mentalhealthapp.journalROOMdatabase

import androidx.compose.runtime.Immutable
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Immutable
@Dao
interface JournalDAO {
    @Insert
    suspend fun insertJournal(journalEntity: JournalEntity)

    @Delete
    suspend fun deleteJournal(journalEntity: JournalEntity)

    @Update
    suspend fun updateJournal(journalEntity: JournalEntity)

    @Query("SELECT * FROM Journal_table ORDER BY date DESC")
    fun getAllJournals(): Flow<List<JournalEntity>>

}