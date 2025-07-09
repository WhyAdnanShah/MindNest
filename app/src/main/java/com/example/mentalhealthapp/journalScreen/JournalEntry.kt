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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
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

    var imageUri by remember { mutableStateOf<List<Uri?>>(emptyList()) }


    /*          This is a simple Media Picker         */
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
            uris ->
        if (uris.isNotEmpty()) {
            imageUri = uris
            Toast.makeText(context, "Image Selected", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "No Image Selected", Toast.LENGTH_SHORT).show()
        }
    }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .wrapContentSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
//        item{
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                value = titleText,
                onValueChange = { titleText = it },
                label = { Text("Title", fontSize = 20.sp) },
                shape = RoundedCornerShape(15.dp),
            )
//        }

//        item{
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Note") },
                shape = RoundedCornerShape(15.dp)
            )
            Spacer(Modifier.height(16.dp))

//        }

//        item{
            Button(modifier = Modifier.wrapContentSize(),
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text(text = "Select Image")
            }
            Spacer(Modifier.height(16.dp))
//        }

//        item {
            SelectedImages(imageUri as Uri?
            )
//        }
    }
}

@Composable
fun SelectedImages(imageUri: Uri?) {
    Text(text = "Image Preview", fontSize = 20.sp)

    imageUri?.let{
            uris ->
        Image(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = rememberAsyncImagePainter(uris), contentDescription = null)
    }
}
