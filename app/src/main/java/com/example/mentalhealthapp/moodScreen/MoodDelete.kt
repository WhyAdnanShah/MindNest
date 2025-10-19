package com.example.mentalhealthapp.moodScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.example.mentalhealthapp.viewModel.MoodViewModel

@Composable
fun MoodDelete(moodViewModel: MoodViewModel, moodEntity: MoodEntity) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = { showDeleteDialog = true },
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete mood",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(18.dp)
        )
    }

    if (showDeleteDialog) {
        DeleteMoodDialog(
            onDismiss = { showDeleteDialog = false },
            moodViewModel = moodViewModel,
            moodEntity = moodEntity
        )
    }
}

@Composable
fun DeleteMoodDialog(onDismiss: () -> Unit, moodViewModel: MoodViewModel, moodEntity: MoodEntity) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Mood Entry") },
        text = { Text("Are you sure you want to delete this mood entry?") },
        confirmButton = {
            Button(
                onClick = {
                    moodViewModel.removeMood(moodEntity)
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text("Cancel",
                    color = colorResource(R.color.light_red)
                )
            }
        }
    )
}