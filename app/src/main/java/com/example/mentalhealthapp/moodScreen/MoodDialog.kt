package com.example.mentalhealthapp.moodScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.example.mentalhealthapp.navigation.CenteredText
import com.example.mentalhealthapp.viewModel.MoodViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun MoodDialog(onDismiss: () -> Unit, moodViewModel: MoodViewModel) {
    val context = LocalContext.current
    var selected by rememberSaveable { mutableIntStateOf(0) }
    var datePickerCard by rememberSaveable { mutableStateOf(false) }
    var selectedDateMillis by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var moodNote by rememberSaveable { mutableStateOf("") }

    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val formattedDate = remember(selectedDateMillis) {
        dateFormat.format(Date(selectedDateMillis))
    }

    val moodNames = listOf("laughing", "smiling", "neutral", "sad", "dead")
    val moodImages = listOf(
        R.drawable.laughing,
        R.drawable.smiling,
        R.drawable.neutral,
        R.drawable.sad,
        R.drawable.dead
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "How are you feeling?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(
                        onClick = onDismiss ,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Mood Selection
                Text(
                    text = "Select your mood",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ImageRadioGroup(
                    imageResIds = moodImages,
                    selectedIndex = selected,
                    onSelected = { selected = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Note Field
                NoteField(
                    value = moodNote,
                    onValueChange = { moodNote = it },
                    labelText = "What affected your mood?",
                    placeholderText = "Share what's on your mind...",
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Date Picker
                Text(
                    text = "Select date",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerCard = true },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (datePickerCard) {
                    DatePickerDialogModal(
                        onDateSelected = { dateMillis ->
                            dateMillis?.let {
                                selectedDateMillis = it
                            }
                            datePickerCard = false
                        },
                        onDismiss = { datePickerCard = false },
                        initialDateMillis = selectedDateMillis
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cancel Button
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 12.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Cancel",
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Save Button
                    Button(
                        onClick = {
                            val mood = moodNames[selected]
                            val note = moodNote
                            val date = selectedDateMillis
                            val formattedDate = dateFormat.format(Date(date))

                            if (mood.isNotEmpty() && note.isNotEmpty()) {
                                moodViewModel.addMood(
                                    MoodEntity(
                                        mood = mood,
                                        note = note,
                                        date = date,
                                        formattedDate = formattedDate
                                    )
                                )
                                onDismiss()
                            }else{
                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            }
                            Log.d("Mood Save", "Mood: $mood, Note: $note, Date: $date, Formatted: $formattedDate")
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Save Mood", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    initialDateMillis: Long = System.currentTimeMillis()
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("OK", fontWeight = FontWeight.Medium)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancel", fontWeight = FontWeight.Medium)
            }
        }
    ) {
        DatePicker(state = datePickerState)
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
                        width = if (isSelected) 1.dp else 1.dp,
                        color = if (isSelected) colorResource(R.color.cornflower_blue) else Color.LightGray,
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