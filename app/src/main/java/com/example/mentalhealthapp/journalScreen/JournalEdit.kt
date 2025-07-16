package com.example.mentalhealthapp.journalScreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.window.Dialog
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.moodScreen.TitleText
import com.example.mentalhealthapp.viewModel.JournalViewModel
import coil.compose.rememberAsyncImagePainter

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
    var titleOfTheJournal by remember { mutableStateOf(journalEntity.title) }
    var contentOfTheJournal by remember { mutableStateOf(journalEntity.content) }

    Dialog(
        onDismissRequest = onDismiss
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                TitleText("Edit Journal")

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = titleOfTheJournal,
                    onValueChange = { titleOfTheJournal = it },
                    label = { Text("Edit title") },
                    placeholder = { Text("Edit title") } ,
                    shape = RoundedCornerShape(15.dp),
                    singleLine = false,
                    maxLines = 10
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = contentOfTheJournal,
                    onValueChange = { contentOfTheJournal = it },
                    label = { Text("Edit Content") },
                    placeholder = { Text("Edit Content") } ,
                    shape = RoundedCornerShape(15.dp),
                    singleLine = false,
                    maxLines = 10
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Images")
                Spacer(modifier = Modifier.height(16.dp))


                val imageUris = Uri.parse(journalEntity.images)
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.slate_gray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    painter = rememberAsyncImagePainter(imageUris), contentDescription = null
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text(
                            "Cancel",
                            color = colorResource(R.color.light_red)
                        )
                    }

                    Button(
                        onClick = {
                            val updateJournal = journalEntity.copy(
                                title = titleOfTheJournal,
                                content = contentOfTheJournal
                            )
                            journalViewModel.editJournal(updateJournal)
                            onDismiss()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.antique_white)),
                        elevation = ButtonDefaults.buttonElevation(10.dp)
                    ) {
                        Image(modifier = Modifier.size(17.dp), imageVector = Icons.Default.Check, contentDescription = null)
                    }
                }

            }
        }
    }
}
