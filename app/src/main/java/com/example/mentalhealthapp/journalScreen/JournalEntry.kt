package com.example.mentalhealthapp.journalScreen

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.navigation.BottomNavItem
import com.example.mentalhealthapp.viewModel.JournalViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.core.net.toUri

@Stable
@Composable
fun JournalEntry(navController: NavHostController, journalViewModel: JournalViewModel ) {
    /*          There was a problem in here with saving the Image and date... I was saving the 'image' as a 'URI' in the JournalEntity which was wrong as hell...
    *           I had to save the 'image' as a 'string' and the 'date' as a 'String' too (Later changing to Long to not mess up the 'ORDER BY date DESC')                                                                    */

    Log.d("Entry To Journal", "JournalEntry called")
    val context = LocalContext.current

    /*          The Title and the content of the Journal            */
    var titleText by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    /*          Image is remembered as a URI         */
    var imageUri by remember { mutableStateOf<Uri?>(null)}

    /*          This is how u save the date in the journal you dumbooooooooo          */
    val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(System.currentTimeMillis())
    val journalEntity = JournalEntity(
        title = titleText,
        content = noteText,
        date = currentDate ,
        images = imageUri.toString()
    )

    /*          This is a simple Media Picker
                'uris' refers to the list of URI objects that point to the location of the images or videos selected
                 if the uris.isEmpty() then uris will be assigned to the imageUri */
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            uri ->
        if (uri != null) {
            imageUri = uri
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = colorResource(R.color.slate_gray),
                shape = RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp)
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row (Modifier
            .fillMaxWidth().padding(top = 10.dp,end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                onClick = {
                    val newJournalEntry = JournalEntity(
                        title = titleText,
                        content = noteText,
                        date = currentDate ,
                        images = imageUri.toString()
                    )
                    journalViewModel.addJournal(newJournalEntry)
                    navController.navigate(BottomNavItem.Journal.route)
                    {popUpTo(BottomNavItem.Journal.route) {
                        inclusive = true
                    }
                    }

//                    Toast.makeText(context,  journalEntity.content
//                            + journalEntity.title +journalEntity.date
//                            +journalEntity.images, Toast.LENGTH_SHORT).show()

                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.antique_white)),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Image(modifier = Modifier.size(17.dp), imageVector = Icons.Default.Check, contentDescription = null)
            }
            Log.d("Clicked Saved" , "What happened?..." + journalEntity.content+ "\n"
                    + journalEntity.title +"\n"
                    +journalEntity.date
                    +"\n"
                    +journalEntity.images
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 16.dp)
                .wrapContentHeight(),
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Title", fontSize = 20.sp) },
            shape = RoundedCornerShape(15.dp),
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 16.dp)
                .heightIn(min = 500.dp, max = 1000.dp),
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Note") },
            shape = RoundedCornerShape(15.dp)
        )
        Spacer(Modifier.height(16.dp))

        Row (
            Modifier.fillMaxWidth().padding(end = 16.dp),
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

        val uriOfImage = journalEntity.images.toUri()
        Image(
            modifier = Modifier
                .size(200.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.slate_gray),
                    shape = RoundedCornerShape(10.dp)
                ),
            painter = rememberAsyncImagePainter(uriOfImage), contentDescription = null
        )

        Spacer(Modifier.height(16.dp))
    }
}
