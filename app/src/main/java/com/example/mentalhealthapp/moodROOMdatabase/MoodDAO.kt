package com.example.mentalhealthapp.moodROOMdatabase

import androidx.compose.runtime.Immutable
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Immutable
@Dao
interface MoodDAO {
    @Insert
    suspend fun insertMood(moodEntity: MoodEntity)

    @Delete
    suspend fun deleteMood(moodEntity: MoodEntity)

    @Update
    suspend fun updateMood(moodEntity: MoodEntity)

    @Query("SELECT * FROM mood_table WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getMoodsByDateRange(startDate: Long, endDate: Long): Flow<List<MoodEntity>>

    // Get moods for a specific day
    @Query("SELECT * FROM mood_table WHERE date >= :dayStart AND date < :dayEnd ORDER BY date DESC")
    fun getMoodsForDay(dayStart: Long, dayEnd: Long): Flow<List<MoodEntity>>

    @Query("SELECT * FROM mood_table ORDER BY date DESC")
    fun getAllMoods(): Flow<List<MoodEntity>>
}