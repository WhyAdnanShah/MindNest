package com.example.mentalhealthapp.moodScreen

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.database.MoodEntity
import com.example.mentalhealthapp.navigation.CenteredText
import com.example.mentalhealthapp.viewModel.MoodViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MoodDialog(onDismiss: () -> Unit, moodViewModel: MoodViewModel) {

//    //Collecting all remembered moods
//    val allMoods = moodViewModel.allMoods.collectAsState(initial = emptyList())

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
                    labelText = "What affected your mood?",
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
                        onClick = onDismiss,
                        color = Color.Transparent,
                        textColors = colorResource(R.color.light_red),
                    )
                    dialogButtons(
                        text = "Save",
                        onClick = {
                            moodViewModel.addMood(
                                MoodEntity(
                                    mood = images[selected].toString(),
                                    note = moodNote,
                                    date = selectedDateMillis?.let { dateFormat.format(it) }.toString()
                                )
                            )
                            onDismiss()
                        },
                        color = colorResource(R.color.blue_sky),
                        textColors = Color.Black
                    )
                }
//                Toast.makeText(LocalContext.current, "image : " + images[selected].toString() +
//                        " moodNote: " + moodNote + " Date : " + selectedDateMillis?.let { dateFormat.format(it) }
//                    , Toast.LENGTH_SHORT).show()
//                Log.d("Mood Save or Not?", "images[selected].toString() : " + images[selected].toString() +
//                        " moodNote: " + moodNote + " Date : " + selectedDateMillis?.let { dateFormat.format(it) })
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
                        width = if (isSelected) 2.dp else 1.dp,
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
fun NoteField(value: String, onValueChange: (String) -> Unit, labelText: String, placeholderText: String) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
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
fun dialogButtons(text: String, onClick: () -> Unit, color: Color, textColors: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(120.dp, 45.dp),
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(20.dp),
    ) {
        Text(
            text,
            color = textColors,
        )
    }
}