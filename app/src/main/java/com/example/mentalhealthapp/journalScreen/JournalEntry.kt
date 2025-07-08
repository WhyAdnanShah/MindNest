package com.example.mentalhealthapp.journalScreen

import android.net.Uri
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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


@Composable
fun JournalEntry(navHostController: NavHostController) {
    val context = LocalContext.current

    var titleText by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical,
                reverseDirection = true,
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
//
//        val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
//            uris ->Adnan Shah Nxm
//            if (uris.isNotEmpty()) {
//                selectedImageUri = uris[5]
//            }
//            else {
//                Log.d("Photo Picker", "No media selected")
//            }
//        }
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

//        Image(painter = painterResource(selectedImageUri), contentDescription = null)
    }
}