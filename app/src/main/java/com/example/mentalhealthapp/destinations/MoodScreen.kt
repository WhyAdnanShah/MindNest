package com.example.mentalhealthapp.destinations

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.database.MoodEntity
import com.example.mentalhealthapp.navigation.CenteredText
import com.example.mentalhealthapp.viewModel.moodViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.mentalhealthapp.viewModel.moodViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoodScreen(moodViewModel: moodViewModel = viewModel(
    factory = moodViewModelFactory(
        LocalContext.current.applicationContext as Application
    ))
)
{
    Log.d("MoodScreen", "Recomposing MoodScreen")
    val allMoods = moodViewModel.allMoods.collectAsState(initial = emptyList())

    var isMoodCardVisible by remember { mutableStateOf(false) }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = { isMoodCardVisible = true },
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(10.dp),
                containerColor = colorResource(R.color.blue_sky)
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Add Mood",
                    color = colorResource(R.color.rich_black)
                )
            }

            if (isMoodCardVisible) {
                MoodDialog(
                    onDismiss = { isMoodCardVisible = false },
                    moodViewModel = moodViewModel
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            items(allMoods.value) { moodItem ->
                Text(
                    text = "On ${moodItem.date}, I felt ${moodItem.mood} because ${moodItem.note}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }
}


@Composable
fun MoodDialog(onDismiss: () -> Unit, moodViewModel : moodViewModel = viewModel()) {

    //Collecting all remembered moods
    val allMoods = moodViewModel.allMoods.collectAsState(initial = emptyList())

    var selected by remember { mutableIntStateOf(0) }
    var datePickerCard by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    var moodNote by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TitleText(text = "How are you feeling right now?")

                val images = listOf(
                    R.drawable.laughing,
                    R.drawable.smiling,
                    R.drawable.neutral,
                    R.drawable.sad,
                    R.drawable.dead
                )
                ImageRadioGroup(
                    imageResIds = images,
                    selectedIndex = selected,
                    onSelected = { selected = it }
                )

                NoteField(
                    value = moodNote,
                    onValueChange = { moodNote = it },
                    lableText = "What affected your mood?",
                    placeholderText = "Enter Note",
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable { datePickerCard = true },
                    colors = CardDefaults.cardColors(colorResource(R.color.slate_gray))
                ) {
                    CenteredText(
                        text = selectedDateMillis?.let { dateFormat.format(it) } ?: "Pick date",
                        fontSize = 15.sp
                    )
                }

                if (datePickerCard) {
                    DatePickerDialogModal(
                        onDateSelected = {
                            selectedDateMillis = it
                            datePickerCard = false
                        },
                        onDismiss = { datePickerCard = false }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    dialogButtons(
                        text = "Cancel",
                        onClick =  onDismiss,
                        color = colorResource(R.color.indian_red)
                    )
                    dialogButtons(
                        text = "Save",
                        onClick = {
                            moodViewModel.addMood(
                                MoodEntity(
                                    mood = "images[selected].toString()",
                                    note = "moodNote",
                                    date = "good days"
                                )
                            )
                        },
                        color = colorResource(R.color.blue_sky)
                    )
                }
            }
        }
    }
}
@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun ImageRadioGroup(imageResIds: List<Int>, selectedIndex: Int, onSelected: (Int) -> Unit) {
    Row {
        imageResIds.forEachIndexed { index, resId ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = if (isSelected) 4.dp else 2.dp,
                        color = if (isSelected) colorResource(R.color.cornflower_blue) else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onSelected(index) }
            ) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = "Option $index",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x330000FF))
                    )
                }
            }
        }
    }
}

@Composable
fun NoteField(value: String, onValueChange: (String) -> Unit, lableText: String, placeholderText: String) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(lableText) },
        placeholder = { Text(placeholderText) },
        shape = RoundedCornerShape(15.dp),
        singleLine = false,
        maxLines = 10
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun dialogButtons(text: String, onClick: () -> Unit, color: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(120.dp, 45.dp),
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text)
    }
}
