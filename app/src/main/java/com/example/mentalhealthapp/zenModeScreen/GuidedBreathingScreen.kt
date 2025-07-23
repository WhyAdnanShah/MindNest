package com.example.mentalhealthapp.zenModeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.mentalhealthapp.R

enum class Phase{
    Exhale,
    Hold,
    Inhale
}
@Composable
fun GuidedBreathingScreen(navController: NavHostController, title: String, rememberChipIndex: Int)  {
    val exhaleSec : Int
    val holdSec : Int
    val inhaleSec : Int

    Box(
        modifier = Modifier.background(
            color = when(title){
                "Equal Breathing" -> colorResource(R.color.sea_green)
                "Box Breathing" -> colorResource(R.color.light_sea_green)
                else -> colorResource(R.color.polished_pine)
            },
        )
    ){

    }

}