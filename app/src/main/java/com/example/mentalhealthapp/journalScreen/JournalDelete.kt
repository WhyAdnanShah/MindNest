package com.example.mentalhealthapp.journalScreen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.viewModel.JournalViewModel

@Composable
fun JournalDelete(journalEntity: JournalEntity, journalViewModel: JournalViewModel) {
    val context = LocalContext.current
    var deleteBtnClicked by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .wrapContentSize()
            .border(
                width = 0.dp,
                color = colorResource(R.color.light_red),
                shape = RoundedCornerShape(10.dp)
            ),
        onClick = {
            deleteBtnClicked = true
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    )
    {
        Text("Delete", color = colorResource(R.color.light_red))
    }

    if (deleteBtnClicked) {
        DeleteJournalDialog(
            onDismiss = { deleteBtnClicked = false },
            journalViewModel = journalViewModel,
            journalEntity = journalEntity

        )
    }
}

@Composable
fun DeleteJournalDialog(
    onDismiss: () -> Unit, journalViewModel: JournalViewModel, journalEntity: JournalEntity) {
    AlertDialog(
        onDismissRequest = onDismiss,
//        modifier = TODO(),
//        properties = TODO(),
        title = { Text("Are you sure?") },
        text = { Text("Do you want to delete this journal?") },
        confirmButton = {
            Button(onClick = {
                onDismiss
                journalViewModel.removeJournal(journalEntity)
            }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text("Cancel", color = colorResource(R.color.light_red))
            }
        }
    )
}