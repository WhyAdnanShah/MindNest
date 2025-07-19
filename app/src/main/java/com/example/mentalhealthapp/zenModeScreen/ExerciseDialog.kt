package com.example.mentalhealthapp.zenModeScreen

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ExerciseDialog(onDismiss: () -> Unit, title: String) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss){
        Card(
            Modifier.size(400.dp)
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
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    item{
                        AssistChip(
                            onClick = {
                                Toast.makeText(context, "1 min", Toast.LENGTH_SHORT).show()
                            },
                            label = { Text(text = "1 min", fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
//                        leadingIcon = Icon(Icons.Default.),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item{
                        AssistChip(
                            onClick = {
                                Toast.makeText(context, "5 min", Toast.LENGTH_SHORT).show()
                            },
                            label = { Text(text = "5 min", fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
//                        leadingIcon = Icon(Icons.Default.),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item{
                        AssistChip(
                            onClick = {
                                Toast.makeText(context, "10 min", Toast.LENGTH_SHORT).show()
                            },
                            label = { Text(text = "10 min", fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
//                        leadingIcon = Icon(Icons.Default.),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item{
                        AssistChip(
                            onClick = {
                                Toast.makeText(context, "15 min", Toast.LENGTH_SHORT).show()
                            },
                            label = { Text(text = "15 min", fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
//                        leadingIcon = Icon(Icons.Default.),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    item{
                        AssistChip(
                            onClick = {
                                Toast.makeText(context, "20 min", Toast.LENGTH_SHORT).show()
                            },
                            label = { Text(text = "20 min", fontSize = 15.sp) },
                            modifier = Modifier.wrapContentSize(),
//                        leadingIcon = Icon(Icons.Default.),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}