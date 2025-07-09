package com.example.mentalhealthapp.journalScreen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun JournalEntry(navController: NavHostController) {
    Log.d("Entry To Journal", "JournalEntry called")
    val context = LocalContext.current

    var titleText by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<List<Uri>>(emptyList()) }


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
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Title", fontSize = 20.sp) },
            shape = RoundedCornerShape(15.dp),
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 500.dp, max = 1000.dp),
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Note") },
            shape = RoundedCornerShape(15.dp)
        )
        Spacer(Modifier.height(16.dp))

        Row (
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            Button(modifier = Modifier.wrapContentSize(),
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text(text = "Add Images")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyRow (
            modifier = Modifier
                .fillMaxWidth().wrapContentHeight(),

            ){
            items(imageUri){
                    uri->
                Image(
                    modifier = Modifier
                        .width(200.dp).height(200.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    painter = rememberAsyncImagePainter(uri), contentDescription = null
                )
            }
        }
    }
}
