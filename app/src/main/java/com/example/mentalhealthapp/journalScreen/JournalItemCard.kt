package com.example.mentalhealthapp.journalScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import com.example.mentalhealthapp.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalhealthapp.journalROOMdatabase.JournalEntity
import com.example.mentalhealthapp.viewModel.JournalViewModel


@Composable
fun JournalItemCard(journalEntity: JournalEntity, journalViewModel: JournalViewModel) {
    Log.d("JournalItemCard", "JournalItemCard called")
    val context = LocalContext.current

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 20.dp)
            .border(
                width = 1.dp,
                color = colorResource(R.color.slate_gray),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                onClick = {
                    Toast.makeText(context, "Journal Item Clicked", Toast.LENGTH_SHORT).show()
                }
            ),
        shape = RoundedCornerShape(20.dp)
    ){
        Column (
            Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ){
            Text(modifier = Modifier.padding(bottom = 5.dp),
                text = journalEntity.date,
                fontSize = 15.sp
            )
            Text(modifier = Modifier,
                text = journalEntity.title,
                fontSize = 17.sp
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .border(
                            width = 0.dp,
                            color = colorResource(R.color.earth_yellow),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    onClick = {
                        Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                )
                {
                    Text("Edit", color = colorResource(R.color.earth_yellow))
                }
                Spacer(modifier = Modifier.width(10.dp))

                JournalDelete(journalEntity, journalViewModel)

            }
        }
    }
}