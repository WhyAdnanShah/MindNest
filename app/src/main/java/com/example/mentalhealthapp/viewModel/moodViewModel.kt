package com.example.mentalhealthapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mentalhealthapp.database.MoodDatabase
import com.example.mentalhealthapp.database.MoodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class moodViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application.applicationContext,
        MoodDatabase::class.java,
        "mood_database"
    ).build()

    var allMoods: Flow<List<MoodEntity>> = db.moodDao().getAllMoods()

    fun addMood(id: MoodEntity) {
        viewModelScope.launch {
            val moodEntity = MoodEntity(mood = id.mood, note = id.note, date = id.date)
            db.moodDao().insertMood(moodEntity)
        }
    }
}