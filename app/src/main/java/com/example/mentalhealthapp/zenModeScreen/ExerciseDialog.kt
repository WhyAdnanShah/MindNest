package com.example.mentalhealthapp.zenModeScreen

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ExerciseDialog(onDismiss: () -> Unit, title: String) {
    Dialog(onDismissRequest = onDismiss){
        Card(
            Modifier.size(200.dp)
        ){
            Text(text = title)
        }
    }
}