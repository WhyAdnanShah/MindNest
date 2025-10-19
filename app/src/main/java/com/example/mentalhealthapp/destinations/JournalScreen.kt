package com.example.mentalhealthapp.destinations

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.journalScreen.JournalEntry
import com.example.mentalhealthapp.journalScreen.JournalItemCard
import com.example.mentalhealthapp.viewModel.JournalViewModel
import kotlinx.coroutines.flow.Flow
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JournalScreen(journalViewModel: JournalViewModel) {
    Log.d("JournalScreen", "JournalScreen called")
    var isJournalEntry by journalViewModel.isJournalEntry
    val journalData by journalViewModel.allJournals.collectAsState(initial = emptyList())
    val allJournals: Flow<List<JournalEntity>> = journalViewModel.db.journalDao().getAllJournals()

    val ghosty = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.astronaut))

    Scaffold (modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isJournalEntry = true },
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.slate_gray),
                    shape = RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp)
                ),
            horizontalAlignment = if (journalData.isEmpty()) Alignment.CenterHorizontally else Alignment.Start,
            verticalArrangement = if (journalData.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {
            if (journalData.isEmpty()) {
                LottieAnimation(ghosty.value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(250.dp)
                )
                Text(text = "Tap the '+' icon to add a new entry", fontSize =  17.sp)
            } else {
                LazyColumn(
                    modifier = Modifier.scrollable(
                        rememberScrollState(),
                        orientation = Orientation.Vertical
                    )
                ) {
                    items(journalData, key = { it.id }) { journalItem ->
                        JournalItemCard(
                            journalEntity = journalItem,
                            journalViewModel = journalViewModel
                        )

                    }
                }
            }
        }
    }
    if (isJournalEntry){
        JournalEntry(
            onDismiss = { isJournalEntry = false } ,
            journalViewModel
        )
    }
}