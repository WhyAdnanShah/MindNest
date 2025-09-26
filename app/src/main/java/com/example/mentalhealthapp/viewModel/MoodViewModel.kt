package com.example.mentalhealthapp.viewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mentalhealthapp.destinations.MoodScreen
import com.example.mentalhealthapp.moodROOMdatabase.MoodDatabase
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {

    val db = Room.databaseBuilder(
        application.applicationContext,
        MoodDatabase::class.java,
        "mood_database"
    ).fallbackToDestructiveMigration().build()

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

    /*          When the button is expanded or not.             */
    val expandedMoodDetailsButton = MutableStateFlow(false)
    fun toggleExpanded(){
        expandedMoodDetailsButton.value =! expandedMoodDetailsButton.value
    }
    /*          MoodDialog             */
    private val _isMoodCardVisible = mutableStateOf(false)
    val isMoodCardVisible: MutableState<Boolean> = _isMoodCardVisible
}