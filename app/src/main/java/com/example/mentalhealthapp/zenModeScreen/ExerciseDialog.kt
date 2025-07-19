package com.example.mentalhealthapp.zenModeScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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

@Composable
fun ExerciseDialog(onDismiss: () -> Unit, title: String) {
    val context = LocalContext.current
    var chipEnabled by remember { mutableStateOf(false) }
    val timings = listOf("1 min","5 min","10 min","15 min","20 min")
    var rememberChipIndex by remember { mutableIntStateOf(-1) }
    Dialog(onDismissRequest = onDismiss){
        Card(
            Modifier.wrapContentSize()
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
                                Toast.makeText(context, timings[index], Toast.LENGTH_SHORT).show()
                                rememberChipIndex = if (isSelected) -1 else index
                                chipEnabled = true
                            },
                            label = { Text(text = timings[index], fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(10.dp),
                            colors = if (isSelected) AssistChipDefaults.assistChipColors(colorResource(R.color.polished_pine) )else AssistChipDefaults.assistChipColors(Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick =  onDismiss,
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text("Cancel", color = colorResource(R.color.indian_red))
                    }

                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { Toast.makeText(context, "Start Exercise", Toast.LENGTH_SHORT).show() }
                    ) {
                        Text("Start")
                    }
                }

            }
        }
    }
}