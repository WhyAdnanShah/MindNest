package com.example.mentalhealthapp.journalScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.window.Dialog
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.viewModel.JournalViewModel

@Composable
fun JournalEdit(journalEntity: JournalEntity, journalViewModel: JournalViewModel){
    val context = LocalContext.current
    var journalEditDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .wrapContentSize()
            .border(
                width = 0.dp,
                color = colorResource(R.color.earth_yellow),
                shape = RoundedCornerShape(10.dp)
            ),
        onClick = {
            journalEditDialog = true
//            Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    )
    {
        Text("Edit", color = colorResource(R.color.earth_yellow))
    }

    if (journalEditDialog) EditJournalDialog(
        onDismiss = { journalEditDialog = false },
        journalEntity = journalEntity,
        journalViewModel = journalViewModel
    )

}

@Composable
fun EditJournalDialog(
    onDismiss: () -> Unit,
    journalViewModel: JournalViewModel,
    journalEntity: JournalEntity
) {
    Dialog(
        onDismissRequest = onDismiss,
    ){
        Card(
            modifier = Modifier.wrapContentSize()
        ){

        }
    }
}
