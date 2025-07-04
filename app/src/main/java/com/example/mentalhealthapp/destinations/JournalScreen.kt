package com.example.mentalhealthapp.destinations

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.viewModel.JournalViewModel

@Composable
fun JournalScreen(journalViewModel: JournalViewModel, navController: NavHostController) {
    val context = LocalContext.current



    var isJournalDetail by remember { mutableStateOf(false) }

    Scaffold (modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    navController.navigate("noteList"){
//                    popUpTo("noteList"){
//                        inclusive = true
//                }
                    Toast.makeText(context, "Add Journal", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(10.dp),
                containerColor = colorResource(R.color.blue_sky)
            ){
                Text("+",
                    fontSize = 25.sp,
                    color = colorResource(R.color.rich_black)
                )
            }
        }
    ){
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.slate_gray),
                    shape = RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp)
                )
        ){}


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Your Journal",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

//        if (isJournalDetail){
//            JournalDetailScreen("noteId")
//        }
    }
}

//@Composable
//fun JournalDetailScreen(noteId: String) {
//    Text("Helloscisnc")
//}