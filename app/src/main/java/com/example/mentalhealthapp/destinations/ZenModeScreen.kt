package com.example.mentalhealthapp.destinations

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.zenModeScreen.ExerciseDialog
import com.example.mentalhealthapp.zenModeScreen.InfoDialog


@Composable
fun ZenModeScreen() {
    Log.d("ZenModeScreen", "ZenModeScreen called")
    val context = LocalContext.current

    val items = listOf("Equal Breathing", "Box Breathing", "4-7-8 Breathing")
    val itemsDetails = listOf("Equal Breathing helps you to relax and focus", "Box Breathing is powerful stress reliever", "4-7-8 Breathing promotes better sleep")
    var isInfoButtonClicked by remember { mutableStateOf(false) }
    var rememberIndex by remember { mutableIntStateOf(0) }
    var exerciseButton by remember { mutableStateOf(false) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(200.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement =Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ){
        items(items.size){
                index ->
            Card (
                Modifier
                    .size(200.dp, 300.dp)
                    .clickable(
                        onClick = {
                            exerciseButton = true
                            rememberIndex = index
                        }
                    ),
                colors = if (items[index] == "Equal Breathing") CardDefaults.cardColors(colorResource(R.color.sea_green))
                else if(items[index] == "Box Breathing") CardDefaults.cardColors(colorResource(R.color.light_sea_green))
                else CardDefaults.cardColors(colorResource(R.color.polished_pine))
            ){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable(
                                onClick = {
                                    isInfoButtonClicked = true
                                    rememberIndex = index
                                }
                            ),
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
                Column (modifier = Modifier.padding(16.dp))
                {
                    Text(items[index],
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(itemsDetails[index],
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
    if (isInfoButtonClicked) {
        InfoDialog(
            onDismiss = { isInfoButtonClicked = false },
            title = items[rememberIndex]
        )
    }
    if (exerciseButton){
        ExerciseDialog(
            onDismiss = { exerciseButton = false },
            title = items[rememberIndex]
        )
    }
}