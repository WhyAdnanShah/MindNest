package com.example.mentalhealthapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mentalhealthapp.journalROOMdatabase.JournalDatabase
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import kotlinx.coroutines.launch

class JournalViewModel (application : Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application.applicationContext,
        JournalDatabase::class.java,
        "journal_database"
    ).build()

    var allJournals = db.journalDao().getAllJournals()

    fun addJournal(journalEntity: JournalEntity){
        viewModelScope.launch {
            db.journalDao().insertJournal(journalEntity)
        }
    }

    fun removeJournal(journalEntity: JournalEntity){
        viewModelScope.launch {
            db.journalDao().deleteJournal(journalEntity)
        }
    }

    fun editJournal(journalEntity: JournalEntity){
        viewModelScope.launch {
            db.journalDao().updateJournal(journalEntity)
        }
    }
}