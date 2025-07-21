package com.example.mentalhealthapp.zenModeScreen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mentalhealthapp.R
import kotlin.math.exp

@Composable
fun ExerciseDialog(onDismiss: () -> Unit, title: String) {
    val context = LocalContext.current
    val timings = listOf("1 min","5 min","10 min","15 min","20 min")
    var rememberChipIndex by remember { mutableIntStateOf(-1) }

    Dialog(onDismissRequest = onDismiss){
        Card(
            Modifier.wrapContentSize(),
            colors = when (title) {
                "Equal Breathing" -> CardDefaults.cardColors(colorResource(R.color.sea_green))
                "Box Breathing" -> CardDefaults.cardColors(colorResource(R.color.light_sea_green))
                else -> CardDefaults.cardColors(colorResource(R.color.polished_pine))
            }
        ){
            Column (
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(text = title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Select time",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
                LazyRow (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    items(timings.size) { index ->
                        val isSelected  = index == rememberChipIndex
                        AssistChip(
                            onClick = {
                                rememberChipIndex = if (isSelected) -1 else index
                            },
                            label = { Text(text = timings[index], fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(10.dp),
                            colors = if (isSelected) AssistChipDefaults.assistChipColors(colorResource(R.color.dim_gray) )else AssistChipDefaults.assistChipColors(Color.Transparent),
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick =  onDismiss,
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        shape = RoundedCornerShape(40.dp)
                    ) {
                        Text("Cancel", color = colorResource(R.color.indian_red), fontWeight = FontWeight.ExtraBold)
                    }

                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            when (rememberChipIndex){
                                0-> Toast.makeText(context, "1 min Exercise $title", Toast.LENGTH_SHORT).show()
                                1-> Toast.makeText(context, "5 min Exercise $title", Toast.LENGTH_SHORT).show()
                                2-> Toast.makeText(context, "10 min Exercise $title", Toast.LENGTH_SHORT).show()
                                3-> Toast.makeText(context, "15 min Exercise $title", Toast.LENGTH_SHORT).show()
                                4-> Toast.makeText(context, "20 min Exercise $title", Toast.LENGTH_SHORT).show()
                                else -> Toast.makeText(context, "Select a time for $title", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.baby_blue)),
                        shape = RoundedCornerShape(40.dp)
                    ) {
                        Text("Start")
                    }
                }

            }
        }
    }
}