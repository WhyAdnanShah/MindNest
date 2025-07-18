package com.example.mentalhealthapp.destinations

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.example.mentalhealthapp.moodScreen.TitleText
import com.example.mentalhealthapp.viewModel.JournalViewModel
import com.example.mentalhealthapp.viewModel.MoodViewModel


@Composable
fun SettingsScreen(){
    Log.d("SettingsScreen","SettingsScreen Called")
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("DarkMode", Context.MODE_PRIVATE)
    val isDarkMode = sharedPreferences.getBoolean("isDarkMode", true)


    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ){
        Text(text = "Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace
        )
        Spacer(Modifier.height(16.dp))
        TitleText(text = "Theme")
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
                colorText = colorResource(R.color.floral_white),
                borderColor = colorResource(R.color.floral_white),
                onClick = { Toast.makeText(context, "Dark Mode", Toast.LENGTH_SHORT).show() }
            )
            ThemeCard(
                text = "Light Mode",
                color = colorResource(R.color.floral_white),
                colorText = colorResource(R.color.black),
                borderColor = colorResource(R.color.wheat),
                onClick = { Toast.makeText(context, "Light Mode", Toast.LENGTH_SHORT).show() }
            )
        }
        Spacer(Modifier.height(50.dp))

        TitleText(text = "Manage data")
        Spacer(Modifier.height(16.dp))

        Text(modifier = Modifier.clickable(
            onClick = {
                Toast.makeText(context, "Clear all Data for Mood Tracker", Toast.LENGTH_SHORT).show()
            }
        ), text = "Clear all Data for Mood Tracker",
            fontSize = 20.sp,
            color = colorResource(R.color.indian_red)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.clickable(
                onClick = {
                    Toast.makeText(context, "Clear all Data for Journals", Toast.LENGTH_SHORT).show()

                }
            ), text = "Clear all Data for Journals",
            fontSize = 20.sp,
            color = colorResource(R.color.indian_red)
        )


        Spacer(Modifier.height(50.dp))

        TitleText(text = "About",
//            fontSize = 20.sp
        )

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.slate_gray),
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                val githubPageIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/WhyAdnanShah/MindNest"))
                context.startActivity(githubPageIntent)
            }
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text ="Github Page", fontSize = 17.sp)
                Image(painter = painterResource(R.drawable.arrow), contentDescription = null, modifier = Modifier.size(17.dp))
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.slate_gray),
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                val linkedInPageIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/in/whyadnan"))
                context.startActivity(linkedInPageIntent)
            }
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text ="LinkedIn Page", fontSize = 17.sp)
                Image(painter = painterResource(R.drawable.arrow), contentDescription = null, modifier = Modifier.size(17.dp))
            }
        }
    }
}

@Composable
fun ThemeCard(text: String, color: Color, colorText: Color, borderColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(150.dp),
        colors = CardDefaults.cardColors(color),
        shape = RoundedCornerShape(20.dp),
        border = (
                BorderStroke(
                    width = 1.dp,
                    color = borderColor,
                )
                ),
        onClick =  onClick
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
                color = colorText
            )
        }
    }
}