package com.example.mentalhealthapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JournalViewModelFactory (private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JournalViewModel(application) as T
    }
}