package com.example.mentalhealthapp.destinations

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.navigation.CenteredText
import com.example.mentalhealthapp.viewModel.JournalViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun JournalScreen(journalViewModel: JournalViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val journalData by journalViewModel.allJournals.collectAsState(initial = emptyList())
    val allJournals: Flow<List<JournalEntity>> = journalViewModel.db.journalDao().getAllJournals()


    Scaffold (modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("makeNote")
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

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ){
            if (allJournals.collectAsState(initial = emptyList()).value.isEmpty()){
                CenteredText("Tap the '+' icon to add a new entry", 15.sp)
            }
            else{
                LazyColumn (
                    modifier = Modifier.scrollable(
                        rememberScrollState(),
                        orientation = Orientation.Vertical
                    )
                ){
                    items(journalData, key = { it.id }){
                            journalItem ->

                    }
                }
            }
        }
    }
}

@Composable
fun JournalEntry(navHostController: NavHostController) {
    CenteredText("Journal Detail Screen", 20.sp)
}