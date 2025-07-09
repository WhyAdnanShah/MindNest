package com.example.mentalhealthapp.journalScreen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter


@Composable
fun JournalEntry(navHostController: NavHostController) {
    Log.d("Entry To Journal", "JournalEntry called")
    val context = LocalContext.current

    var titleText by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }


    /*          This is a simple Media Picker         */
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            uri ->
        if (uri != null) {
            imageUri= uri
            Toast.makeText(context, "Image Selected", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "No Image Selected", Toast.LENGTH_SHORT).show()

        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollState()
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Title", fontSize = 20.sp) },
            shape = RoundedCornerShape(15.dp),
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Note") },
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(Modifier.height(10.dp))

        Button(modifier = Modifier.wrapContentSize(),
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            Text(text = "Select Image")
        }

        Text(text = "Image Preview", fontSize = 20.sp)

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){

            imageUri?.let{
                    uri ->
                Image(
                    modifier = Modifier
                        .width(200.dp).height(300.dp),
//                        .clip(RoundedCornerShape(10.dp)),
                    painter = rememberAsyncImagePainter(uri), contentDescription = null)
            }
        }


    }
}