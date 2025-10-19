package com.example.mentalhealthapp.destinations

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.viewModel.MoodViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.example.mentalhealthapp.moodScreen.MoodDialog
import com.example.mentalhealthapp.moodScreen.MoodItemCard
import com.example.mentalhealthapp.moodScreen.MoodLineChart
import kotlinx.coroutines.flow.Flow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoodScreen(moodViewModel: MoodViewModel) {
    Log.d("MoodScreen", "Recomposing MoodScreen")
    var isMoodCardVisible by moodViewModel.isMoodCardVisible

    val moodsData by moodViewModel.allMoods.collectAsState(initial = emptyList())
    val allMoods: Flow<List<MoodEntity>> = moodViewModel.db.moodDao().getAllMoods()

    // Animations
    val expandedMoodDetailsButton by moodViewModel.expandedMoodDetailsButton.collectAsState()
    val rotation by animateFloatAsState(
        targetValue = if (expandedMoodDetailsButton) 180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val moodDetailHeight by animateDpAsState(
        targetValue = if (expandedMoodDetailsButton) 600.dp else 400.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    val moodEmojis = listOf("laughing", "smiling", "neutral", "sad", "dead")
    val rememberMoodChipIndex = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isMoodCardVisible = true },
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add mood"
                    )
                },
                text = {
                    Text(
                        text = "Add Snapshot",
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with stats
            MoodStatsHeader(moodsData)

            // Moment Snapshots Section
            MomentSnapshotsSection(
                allMoods = allMoods,
                expandedMoodDetailsButton = expandedMoodDetailsButton,
                moodDetailHeight = moodDetailHeight,
                moodViewModel = moodViewModel,
                rotation = rotation,
                moodsData = moodsData,
                moodEmojis = moodEmojis,
                rememberMoodChipIndex = rememberMoodChipIndex,
                onAddMood = { isMoodCardVisible = true }
            )

            // Trend Analysis Section
            TrendAnalysisSection(
                allMoods = allMoods,
                moodsData = moodsData,
                rememberMoodChipIndex = rememberMoodChipIndex
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

@Composable
fun MoodStatsHeader(moodsData: List<MoodEntity>) {
    val currentMood = moodsData.lastOrNull()?.mood ?: "neutral"
    val moodCount = moodsData.size

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Current Mood",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(getMoodDrawable(currentMood)),
                    contentDescription = "Current mood",
                    modifier = Modifier.size(40.dp)
                )
            }

            Divider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = moodCount.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Total Entries",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun MomentSnapshotsSection(
    allMoods: Flow<List<MoodEntity>>,
    expandedMoodDetailsButton: Boolean,
    moodDetailHeight: Dp,
    moodViewModel: MoodViewModel,
    rotation: Float,
    moodsData: List<MoodEntity>,
    moodEmojis: List<String>,
    rememberMoodChipIndex: MutableState<String>,
    onAddMood: () -> Unit
) {
    val isEmpty = allMoods.collectAsState(initial = emptyList()).value.isEmpty()
    val noDataFox = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_data))


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            if (!isEmpty) {
                moodViewModel.toggleExpanded()
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Moment Snapshots",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (!isEmpty) {
                    IconButton(
                        onClick = { moodViewModel.toggleExpanded() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = if (expandedMoodDetailsButton) painterResource(R.drawable.collapse) else painterResource(R.drawable.expand),
                            contentDescription = if (expandedMoodDetailsButton) "Collapse" else "Expand",
                            modifier = Modifier.rotate(rotation),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Content
            AnimatedVisibility(
                visible = expandedMoodDetailsButton || isEmpty,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = moodDetailHeight)
                ) {
                    if (isEmpty) {
                        EmptyState(
                            title = "No Snapshots Yet",
                            description = "Tap the + button to add your first mood snapshot",
                            animation = noDataFox
                        )
                    } else {
                        // Filter Chips
                        MoodFilterSection(
                            moodEmojis = moodEmojis,
                            rememberMoodChipIndex = rememberMoodChipIndex,
                            expandedMoodDetailsButton = expandedMoodDetailsButton
                        )

                        // Mood List
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(
                                items = when {
                                    rememberMoodChipIndex.value.isNotEmpty() ->
                                        moodsData.filter { it.mood == rememberMoodChipIndex.value }
                                    else -> moodsData
                                },
                                key = { it.id }
                            ) { moodItem ->
                                MoodItemCard(
                                    moodEntity = moodItem,
                                    moodViewModel = moodViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoodFilterSection(
    moodEmojis: List<String>,
    rememberMoodChipIndex: MutableState<String>,
    expandedMoodDetailsButton: Boolean
) {
    AnimatedVisibility(
        visible = expandedMoodDetailsButton,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Filter by Mood",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(moodEmojis) { mood ->
                    val isSelected = mood == rememberMoodChipIndex.value
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            rememberMoodChipIndex.value = if (isSelected) "" else mood
                        },
                        label = {
                            Image(
                                painter = painterResource(getMoodDrawable(mood)),
                                contentDescription = mood,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun TrendAnalysisSection(
    allMoods: Flow<List<MoodEntity>>,
    moodsData: List<MoodEntity>,
    rememberMoodChipIndex: MutableState<String>
) {
    val isEmpty = allMoods.collectAsState(initial = emptyList()).value.isEmpty()
    val noDataChart = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chart_animation))


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(400.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Trend Analysis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isEmpty) {
                EmptyState(
                    title = "No Data Yet",
                    description = "Add mood entries to see your trends",
                    animation = noDataChart
                )
            } else {
                MoodLineChart(
                    moodData = moodsData,
                    rememberMoodChipIndex = rememberMoodChipIndex
                )
            }
        }
    }
}

@Composable
fun EmptyState(
    title: String,
    description: String,
    animation: LottieCompositionResult
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(animation.value, iterations = LottieConstants.IterateForever ,modifier = Modifier
            .size(200.dp)
            .align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

fun getMoodDrawable(mood: String): Int {
    return when (mood) {
        "laughing" -> R.drawable.laughing
        "smiling" -> R.drawable.smiling
        "neutral" -> R.drawable.neutral
        "sad" -> R.drawable.sad
        else -> R.drawable.dead
    }
}