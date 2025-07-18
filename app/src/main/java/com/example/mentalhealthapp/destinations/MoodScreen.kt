package com.example.mentalhealthapp.destinations

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.example.mentalhealthapp.moodScreen.MoodDialog
import com.example.mentalhealthapp.moodScreen.MoodItemCard
import com.example.mentalhealthapp.moodScreen.MoodLineChart
import com.example.mentalhealthapp.navigation.CenteredText
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Stable
fun MoodScreen(moodViewModel: MoodViewModel) {
    Log.d("MoodScreen", "Recomposing MoodScreen")

    var isMoodCardVisible by remember { mutableStateOf(false) }

    val moodsData by moodViewModel.allMoods.collectAsState(initial = emptyList())

    //These are all the moods that are in the DataBase in a ListView... This is to check if there is any item in the DataBase or not.
    val allMoods: Flow<List<MoodEntity>> = moodViewModel.db.moodDao().getAllMoods()

    //Animations that I like
    var expandedMoodDetailsButton by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (expandedMoodDetailsButton) 270f else 0f)
    val moodDetailHeight = if (expandedMoodDetailsButton) 1000.dp else 450.dp



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
                .padding(vertical = 10.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            700, easing = FastOutSlowInEasing
                        )
                    )
                    .height(moodDetailHeight)
                    .border(
                        shape = RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp),
                        width = 0.dp,
                        color = colorResource(R.color.slate_gray)
                    )


            ) {
                if (allMoods.collectAsState(initial = emptyList()).value.isEmpty()){
                    CenteredText("Tap the '+' icon to add a new snapshot", fontSize = 15.sp,)
                }
                else{
                    LazyColumn (
                        modifier = Modifier.scrollable(
                            rememberScrollState(),
                            orientation = Orientation.Vertical
                        )
                    ){

                        item {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 5.dp),
                                horizontalArrangement = Arrangement.End)
                            {
                                Button(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .border(
                                            shape = RoundedCornerShape(20.dp),
                                            color = colorResource(R.color.slate_gray),
                                            width = 1.dp
                                        ),
                                    onClick = {
                                        expandedMoodDetailsButton = !expandedMoodDetailsButton
                                    },
                                    colors = buttonColors(colorResource(R.color.transparent))
                                ) {
                                    Image(modifier = Modifier
                                        .size(17.dp)
                                        .rotate(rotation),
                                        painter = if (expandedMoodDetailsButton) painterResource(R.drawable.collapse)
                                        else painterResource(R.drawable.expand),
                                        contentDescription = null
                                    )
                                }
                            }

                        }

                        items(moodsData, key= {it.id}) { moodItem ->
                            MoodItemCard(
                                moodEntity = moodItem,
                                moodViewModel = moodViewModel
                            )
                        }

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
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .border(
                        shape = RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp),
                        width = 0.dp,
                        color = colorResource(R.color.slate_gray)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (allMoods.collectAsState(initial = emptyList()).value.isEmpty()){
                    Image(modifier = Modifier.size(150.dp), painter = painterResource(R.drawable.empty_list), contentDescription = "Empty List")
                    Text("Tap the '+' icon to add a new snapshot",
                        fontSize = 15.sp
                    )
                }
                else{
                    MoodLineChart(
                        moodData = moodsData
                    )
                }

            }

        }

        if (isMoodCardVisible) {
            MoodDialog(
                onDismiss = { isMoodCardVisible = false },
                moodViewModel = moodViewModel
            )
        }
    }
}

