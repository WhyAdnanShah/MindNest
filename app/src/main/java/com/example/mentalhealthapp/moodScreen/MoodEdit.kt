package com.example.mentalhealthapp.moodScreen

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.example.mentalhealthapp.viewModel.MoodViewModel
import kotlin.collections.listOf

@Composable
fun MoodEdit(
    moodViewModel: MoodViewModel,
    moods: MoodEntity
) {
    val context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }


    Button(
        modifier = Modifier
            .wrapContentSize(),
        onClick = {
            showEditDialog = true
            moodViewModel
        },
        border = (
                BorderStroke(
                    width = 0.dp,
                    color = colorResource(R.color.earth_yellow)
                )
                ),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Image(painter = painterResource(R.drawable.edit), contentDescription = null, modifier = Modifier.size(13.dp))
        Spacer(Modifier.width(5.dp))
        Text(
            text = "Edit",
            fontSize = 13.sp,
            color = colorResource(R.color.earth_yellow)

        )
    }

    if (showEditDialog) EditMoodDialog(
        onDismiss = { showEditDialog = false },
        moodViewModel = moodViewModel,
        moods = moods,
    )
}

@Composable
fun EditMoodDialog(
    onDismiss: () -> Unit,
    moodViewModel: MoodViewModel,
    moods: MoodEntity
) {
    val moodNames = listOf("laughing", "smiling", "neutral", "sad", "dead")
    val images = listOf(
        R.drawable.laughing,
        R.drawable.smiling,
        R.drawable.neutral,
        R.drawable.sad ,
        R.drawable.dead
    )
    var selected by remember { mutableIntStateOf(0) }
    var moodNoteEdit by remember { mutableStateOf(moods.note) }



    Dialog(onDismissRequest = onDismiss)
    {
        Card(modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
        ){
            Column (modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ){
                TitleText(text = "Edit Mood Entry")

                Spacer(modifier = Modifier.height(25.dp))

                ImageGroup(
                    imageResIds = images,
                    selectedIndex = selected,
                    onSelected = { selected = it }
                )

                Spacer(modifier = Modifier.height(25.dp))

                EditNoteField(
                    value = moodNoteEdit,
                    onValueChange = { moodNoteEdit = it },
                    labelText = "What affected your mood?",
                    placeholderText = "Enter Note",
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text(
                            "Cancel",
                            color = colorResource(R.color.light_red)
                        )
                    }

                    Button(
                        onClick = {
                            val updatedMood = moods.copy(
                                mood = moodNames[selected],
                                note = moodNoteEdit
                            )
                            moodViewModel.editMood(updatedMood)
                            onDismiss()


                        }
                    ) {
                        Text("Confirm")
                    }
//                    Toast.makeText(LocalContext.current, "image : " + images[selected].toString() +
//                            " moodNote: " + moodNoteEdit
//                        , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun EditNoteField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    placeholderText: String
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        placeholder = { Text(placeholderText) },
        shape = RoundedCornerShape(15.dp),
        singleLine = false,
        maxLines = 10
    )
}


@Composable
fun ImageGroup(imageResIds: List<Int>, selectedIndex: Int, onSelected: (Int) -> Unit) {
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
