package com.example.mentalhealthapp.destinations

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.viewModel.MoodViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.mentalhealthapp.moodScreen.MoodDialog
import com.example.mentalhealthapp.viewModel.MoodViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoodScreen(moodViewModel: MoodViewModel = viewModel(
    factory = MoodViewModelFactory(
        LocalContext.current.applicationContext as Application
    ))
)
{
    Log.d("MoodScreen", "Recomposing MoodScreen")

    var isMoodCardVisible by remember { mutableStateOf(false) }

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = { isMoodCardVisible = true },
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(10.dp),
                containerColor = colorResource(R.color.blue_sky)
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Add Mood",
                    color = colorResource(R.color.rich_black)
                )
            }

            if (isMoodCardVisible) {
                MoodDialog(
                    onDismiss = { isMoodCardVisible = false },
                    moodViewModel = moodViewModel
                )
                Log.d("Mood Dialog", "Mood Dialog Opened?")
            }
        }
    }

    val moods by moodViewModel.allMoods.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            items(moods) { moodItem ->
                Text(
                    text = "${moodItem.mood} ${moodItem.note} ${moodItem.date}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }
}