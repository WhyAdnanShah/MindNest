package com.example.mentalhealthapp.journalScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mentalhealthapp.navigation.CenteredText

@Composable
fun JournalEntry(navHostController: NavHostController) {
    CenteredText("Journal Detail Screen", 20.sp)
}