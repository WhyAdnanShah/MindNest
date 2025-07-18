package com.example.mentalhealthapp.destinations

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mentalhealthapp.R


@Composable
fun ZenModeScreen() {
    val context = LocalContext.current

    val items = listOf("Equal Breathing", "Box Breathing", "4-7-8 Breathing")
    var isInfoButtonClicked by remember { mutableStateOf(false) }
    var rememberIndex by remember { mutableIntStateOf(0) }

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
                            Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show()
                        }
                    ),
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
                }
            }
        }
    }
    if (isInfoButtonClicked) {
        InfoDialog(
            onDismiss = { isInfoButtonClicked = false },
            title = items[rememberIndex],
        )
    }
}

@Composable
fun InfoDialog(onDismiss: () -> Unit, title: String) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Card (
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ){
            Text(modifier = Modifier.padding(16.dp),
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            when (title) {
                "Equal Breathing" -> {
                    Text(modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.Equal_Breathing),
                        fontSize = 17.sp
                    )
                }
                "Box Breathing" -> {
                    Text(modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.Box_Breathing),
                        fontSize = 17.sp
                    )
                }
                else -> {
                    Text(modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.four_seven_eight_Breathing),
                        fontSize = 17.sp
                    )
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.indian_red),
                        shape = RoundedCornerShape(20.dp)
                    ),
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text("Dismiss", color = colorResource(R.color.indian_red))
            }
        }
    }
}