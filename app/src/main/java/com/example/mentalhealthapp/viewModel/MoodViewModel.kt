package com.example.mentalhealthapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mentalhealthapp.destinations.MoodScreen
import com.example.mentalhealthapp.moodROOMdatabase.MoodDatabase
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {

    val db = Room.databaseBuilder(
        application.applicationContext,
        MoodDatabase::class.java,
        "mood_database"
    ).build()

    var allMoods: Flow<List<MoodEntity>> = db.moodDao().getAllMoods()

    fun addMood(moodEntity: MoodEntity) {
        viewModelScope.launch {
            db.moodDao().insertMood(moodEntity)
        }
    }

    fun removeMood(moodEntity: MoodEntity){
        viewModelScope.launch {
            db.moodDao().deleteMood(moodEntity)
        }
    }

    fun editMood(moodEntity: MoodEntity){
        viewModelScope.launch {
            db.moodDao().updateMood(moodEntity)
        }
    }
}