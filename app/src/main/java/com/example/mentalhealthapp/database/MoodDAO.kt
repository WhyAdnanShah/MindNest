package com.example.mentalhealthapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDAO {
    @Insert
    suspend fun insertMood(moodEntity: MoodEntity)

    @Delete
    suspend fun deleteMood(moodEntity: MoodEntity)

    @Update
    suspend fun updateMood(moodEntity: MoodEntity)

    @Query("SELECT * FROM mood_table ORDER BY date DESC")
    fun getAllMoods(): Flow<List<MoodEntity>>
}