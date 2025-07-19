package com.example.mentalhealthapp.zenModeScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun InfoDialog(onDismiss: () -> Unit, title: String) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Card (
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            colors = when(title){
                "Equal Breathing" -> CardDefaults.cardColors(colorResource(R.color.sea_green))
                "Box Breathing" -> CardDefaults.cardColors(colorResource(R.color.light_sea_green))
                else -> CardDefaults.cardColors(colorResource(R.color.polished_pine))

            }
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