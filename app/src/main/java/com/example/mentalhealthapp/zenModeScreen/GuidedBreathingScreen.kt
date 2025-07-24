package com.example.mentalhealthapp.zenModeScreen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.util.TableInfo
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.moodScreen.TitleText
import com.example.mentalhealthapp.navigation.BottomNavItem
import kotlinx.coroutines.delay
import java.nio.file.WatchEvent


@Composable
fun GuidedBreathingScreen(navController: NavHostController, title: String, rememberChipIndex: Int)  {
    val context = LocalContext.current
    var initialSec by remember { mutableIntStateOf(10) }
    LaunchedEffect(initialSec) {
        while (initialSec > 0){
            delay(1000L)
            initialSec--
        }
    }
    var totalTime by remember { mutableIntStateOf(
        when (rememberChipIndex){
            0-> 60
            1 -> 60*5
            2-> 60*10
            3-> 60*15
            4-> 60*20
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

    val expandingCircle by animateDpAsState(
        targetValue = when{
            initialSec > 0 -> 100.dp
            inhaleSec > 0 -> 200.dp
            holdSec > 0 -> 150.dp
            exhaleSec > 0 -> 400.dp
            else -> 200.dp
        }, animationSpec = tween(durationMillis = 1000)
    )


    when(title){
        "Equal Breathing" -> {
            when{
                initialSec == 0 -> {
                    LaunchedEffect(totalTime) {
                        while (totalTime > 0 ){
                            delay(1000L)
                            when{
                                inhaleSec > 0 -> inhaleSec--
                                exhaleSec > 0 -> exhaleSec--
                                else -> {
                                    inhaleSec = 4
                                    exhaleSec = 4
                                }
                            }
                            totalTime--
                        }
                    }
                }
            }
        }
        "Box Breathing" -> {
            when{
                initialSec == 0 ->{
                    LaunchedEffect(totalTime) {
                        while (totalTime > 0){
                            delay(1000L)
                            when{
                                inhaleSec > 0 -> inhaleSec--
                                holdSec > 0 -> holdSec--
                                exhaleSec > 0 -> exhaleSec--
                                else -> {
                                    inhaleSec = 4
                                    holdSec = 4
                                    exhaleSec = 4
                                }
                            }
                            totalTime--
                        }
                    }
                }
            }
        }
        else -> {
            when{
                initialSec == 0 ->{
                    LaunchedEffect(totalTime) {
                        while (totalTime > 0){
                            delay(1000L)
                            when{
                                inhaleSec > 0 -> inhaleSec--
                                holdSec > 0 -> holdSec--
                                exhaleSec > 0 -> exhaleSec--
                                else -> {
                                    inhaleSec = 4
                                    holdSec = 7
                                    exhaleSec = 8
                                }
                            }
                            totalTime--
                        }
                    }
                }
            }
        }
    }

    Log.d("", "")
    val currentPhase = when {
        initialSec > 0 -> "initial"
        inhaleSec > 0 -> "inhale"
        holdSec > 0 -> "hold"
        exhaleSec > 0 -> "exhale"
        else -> "done"
    }

    var lastPlayedPhase by remember { mutableStateOf("") }

    LaunchedEffect(currentPhase) {
        if (currentPhase != lastPlayedPhase && currentPhase != "done") {
            val mediaPlayer = MediaPlayer.create(context, R.raw.bell)
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.start()
            lastPlayedPhase = currentPhase
        }
    }

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
        TitleText("Time remaining: $totalTime")
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
                initialSec >= 5-> "Relax and get Comfortable"
                initialSec > 0 -> "Focus of your breathing"
                inhaleSec > 0 -> "Breathe In"
                holdSec > 0 -> "Hold"
                else -> "Breathe Out"
            }, fontSize = 25.sp
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Card(
            modifier = Modifier
                .size(expandingCircle),
            shape = RoundedCornerShape(400.dp),
            colors = CardDefaults.cardColors((Color.Gray.copy(alpha = 0.3f)))
        ){}
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigateUp() },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.baby_blue)),
            enabled = when(totalTime){
                0 -> true
                else -> false
            }
        ) {
            Text("Go back")
        }
    }
}