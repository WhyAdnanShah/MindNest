package com.example.mentalhealthapp.zenModeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mentalhealthapp.R
import kotlinx.coroutines.delay

@Composable
fun GuidedBreathingScreen(navController: NavHostController, title: String, rememberChipIndex: Int)  {
    var initialSec by remember { mutableIntStateOf(4) }
    var totalTime by remember { mutableIntStateOf(
        when (rememberChipIndex){
            0-> 60000
            1 -> 300000
            2-> 600000
            3-> 900000
            4-> 1200000
            else -> 0
        }
    ) }


    var inhaleSec by remember { mutableIntStateOf(when(title){
        "Equal Breathing" -> 4
        "Box Breathing" -> 4
        else -> 4
    }) }
    var holdSec by remember { mutableIntStateOf(when(title){
        "Equal Breathing" -> 0
        "Box Breathing" -> 4
        else -> 7
    }) }
    var exhaleSec by remember { mutableIntStateOf(when(title){
        "Equal Breathing" -> 4
        "Box Breathing" -> 4
        else -> 8
    }) }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (title) {
                    "Equal Breathing" -> colorResource(R.color.sea_green)
                    "Box Breathing" -> {
                        colorResource(R.color.light_sea_green)
                    }
                    else -> {
                        colorResource(R.color.polished_pine)
                    }
                }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        when(title){
            "Equal Breathing" -> {

                LaunchedEffect(initialSec) {
                    while (initialSec > 0){
                        delay(1_000L)
                        initialSec--
                    }
                }
                when(initialSec){
                    0-> LaunchedEffect(inhaleSec) {
                        while (inhaleSec > 0){
                            delay(1_000L)
                            inhaleSec--
                        }
                    }
                }
                when(inhaleSec){
                    0-> LaunchedEffect(exhaleSec) {
                        while (exhaleSec > 0){
                            delay(1_000L)
                            exhaleSec--
                        }
                    }
                }

            }
            "Box Breathing" -> {

                LaunchedEffect(initialSec) {
                    while (initialSec > 0){
                        delay(1_000L)
                        initialSec--
                    }
                }
                when(initialSec){
                    0-> LaunchedEffect(inhaleSec) {
                        while (inhaleSec > 0){
                            delay(1_000L)
                            inhaleSec--
                        }
                    }
                }
                when(inhaleSec){
                    0-> LaunchedEffect(holdSec) {
                        while (holdSec > 0){
                            delay(1_000L)
                            holdSec--
                        }
                    }
                }
                when(holdSec){
                    0-> LaunchedEffect(exhaleSec) {
                        while (exhaleSec > 0){
                            delay(1_000L)
                            exhaleSec--
                        }
                    }
                }

            }
            else -> {

                LaunchedEffect(initialSec) {
                    while (initialSec > 0){
                        delay(1_000L)
                        initialSec--
                    }
                }
                when(initialSec){
                    0-> LaunchedEffect(inhaleSec) {
                        while (inhaleSec > 0){
                            delay(1_000L)
                            inhaleSec--
                        }
                    }
                }
                when(inhaleSec){
                    0-> LaunchedEffect(holdSec) {
                        while (holdSec > 0){
                            delay(1_000L)
                            holdSec--
                        }
                    }
                }
                when(holdSec){
                    0-> LaunchedEffect(exhaleSec) {
                        while (exhaleSec > 0){
                            delay(1_000L)
                            exhaleSec--
                        }
                    }
                }
            }
        }

        Text(
            text = when {
                initialSec > 0-> "$initialSec"
                inhaleSec > 0 -> "$inhaleSec"
                holdSec > 0 -> "$holdSec"
                else -> "$exhaleSec"
            }, fontSize = 70.sp
        )
        Text(
            text = when {
                initialSec > 0-> "Sit somewhere Comfortable"
                inhaleSec > 0 -> "Inhale"
                holdSec > 0 -> "Hold"
                else -> "Exhale"
            }, fontSize = 25.sp
        )
//        when(exhaleSec){
//            0 -> navController.navigate("ZenModeScreen")
//
//        }




    }



}