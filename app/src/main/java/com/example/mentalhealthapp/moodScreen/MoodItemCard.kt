package com.example.mentalhealthapp.moodScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.database.MoodEntity
import com.example.mentalhealthapp.viewModel.MoodViewModel

@Composable
fun MoodItemCard(moods: MoodEntity, moodViewModel: MoodViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 10.dp, 16.dp, 10.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        border = (
                BorderStroke(
                    width = 0.dp,
                    color = colorResource(R.color.wheat)
                )
                ),

        ){
        Column (
            modifier = Modifier
                .padding(16.dp, 10.dp, 16.dp, 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start

        ){
            Row (
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){ Image(modifier = Modifier.size(40.dp),
                painter = painterResource(
                    when (moods.mood) {
                        "2130968593" -> {
                            R.drawable.laughing
                        }
                        "2130968611" -> {
                            R.drawable.smiling
                        }
                        "2130968595" -> {
                            R.drawable.neutral
                        }
                        "2130968609" -> {
                            R.drawable.sad
                        }
                        "2130968581" -> {
                            R.drawable.dead
                        }
                        else -> {
                            R.drawable.dead
                        }
                    }
                ) ,
                contentDescription = null
            )
                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Date: " + moods.date,
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = moods.note,
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                MoodEdit(
                    moodViewModel = moodViewModel,
                    moods = moods
                )

                Spacer(Modifier.width(10.dp))

                MoodDelete(
                    moodViewModel = moodViewModel,
                    moods = moods
                )
            }
        }
    }
}