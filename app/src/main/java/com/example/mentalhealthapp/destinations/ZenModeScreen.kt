package com.example.mentalhealthapp.destinations

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.navigation.CenteredText


@Composable
fun ZenModeScreen() {
    val context = LocalContext.current

    val items = listOf("Equal Breathing", "Box Breathing", "4-7-8 Breathing")

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(200.dp),
//        state = TODO(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement =Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ){
        items(items.size){
            index ->
            Card (
                Modifier
                    .size(200.dp,300.dp)
                    .clickable(
                        onClick = {
                            Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show()
                        }
                    ),
            ){
                Row(
                    Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable(
                                onClick = { Toast.makeText(context, "Info for ${items[index]}", Toast.LENGTH_SHORT).show() }
                            ),
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
                CenteredText(items[index], fontSize = 20.sp)
            }
        }
    }
}
