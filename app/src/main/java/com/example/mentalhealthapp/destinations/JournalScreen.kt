package com.example.mentalhealthapp.destinations

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.mentalhealthapp.R

@Composable
fun JournalScreen(){
    Column(
        modifier = Modifier.fillMaxSize().padding(5.dp).border(
            width = 1.dp,
            color = colorResource(R.color.slate_gray),
            shape = RoundedCornerShape(5.dp, 5.dp, 20.dp, 20.dp)
        )
    ){

    }
}