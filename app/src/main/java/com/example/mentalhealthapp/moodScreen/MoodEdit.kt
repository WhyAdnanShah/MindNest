package com.example.mentalhealthapp.moodScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.database.MoodEntity
import com.example.mentalhealthapp.viewModel.MoodViewModel

@Composable
fun MoodEdit(moodViewModel: MoodViewModel, moods: MoodEntity) {
    var context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }


    Button(
        modifier = Modifier
            .width(100.dp)
            .height(35.dp),
        onClick = {
            showEditDialog = true
            moodViewModel
            Toast.makeText(context, "Edit id: " + moods.id, Toast.LENGTH_SHORT).show()
        },
        border = (
                BorderStroke(
                    width = 0.dp,
                    color = colorResource(R.color.earth_yellow)
                )
                ),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(
            text = "Edit",
            fontSize = 13.sp,
            color = colorResource(R.color.earth_yellow)

        )
    }

    if (showEditDialog) EditMoodDialog(
        onDismiss = { showEditDialog = false },
        moodViewModel = moodViewModel,
        moods = moods
    )


}

@Composable
fun EditMoodDialog(onDismiss: () -> Unit, moodViewModel: MoodViewModel, moods: MoodEntity) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Mood Entry") },
        text = { Text("Are you sure you want to edit this mood entry?") },
        confirmButton = {
            Button(
                onClick = {
                    moodViewModel.editMood(moods)
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
