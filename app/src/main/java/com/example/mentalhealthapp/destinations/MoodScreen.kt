package com.example.mentalhealthapp.destinations

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.viewModel.MoodViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.mentalhealthapp.moodScreen.MoodDialog
import com.example.mentalhealthapp.moodScreen.MoodItemCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoodScreen(moodViewModel: MoodViewModel) {
    Log.d("MoodScreen", "Recomposing MoodScreen")

    var isMoodCardVisible by remember { mutableStateOf(false) }

    val moods by moodViewModel.allMoods.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isMoodCardVisible = true },
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(10.dp),
                containerColor = colorResource(R.color.blue_sky)
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "+",
                    fontSize = 25.sp,
                    color = colorResource(R.color.rich_black)
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Moment Snapshots",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .border(
                        shape = RoundedCornerShape(20.dp),
                        width = 0.dp,
                        color = colorResource(R.color.wheat)
                    )

            ) {
                LazyColumn{

                    items(moods) { moodItem ->
                        MoodItemCard(moods = moodItem)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Trend Analysis",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

        }

        if (isMoodCardVisible) {
            MoodDialog(
                onDismiss = { isMoodCardVisible = false },
                moodViewModel = moodViewModel
            )
        }
    }
}
