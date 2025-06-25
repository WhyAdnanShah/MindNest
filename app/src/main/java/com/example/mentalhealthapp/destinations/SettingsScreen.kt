package com.example.mentalhealthapp.destinations

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.R


@Composable
fun SettingsScreen(){
    val context = LocalContext.current

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp, 60.dp, 16.dp, 0.dp)
    ){
        Text(text = "Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace
        )
        Spacer(Modifier.height(16.dp))
        Text(text = "Theme",
            fontSize = 20.sp,
        )
        Spacer(Modifier.height(20.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            ThemeCard(
                text = "Dark Mode",
                color = colorResource(R.color.google_grey),
                ColorText = colorResource(R.color.floral_white),
                borderColor = colorResource(R.color.floral_white),
                onClick = Toast.makeText(context, "Dark Mode", Toast.LENGTH_SHORT).show()

            )
            ThemeCard(
                text = "Light Mode",
                color = colorResource(R.color.floral_white),
                ColorText = colorResource(R.color.black),
                borderColor = colorResource(R.color.wheat),
                onClick = Toast.makeText(context, "Light Mode", Toast.LENGTH_SHORT).show()
            )
        }
    }
}

@Composable
fun ThemeCard(text: String, color: Color, ColorText: Color, borderColor: Color, onClick: Any) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(150.dp)
            .clickable(
                onClick = {
                    onClick
                }
            ),
        colors = CardDefaults.cardColors(color),
        border = (
                androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = borderColor,
                )
                ),
        shape = RoundedCornerShape(20.dp)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = ColorText
            )
        }
    }
}