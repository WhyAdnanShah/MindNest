package com.example.mentalhealthapp.moodScreen

import android.view.ViewGroup
import androidx.compose.animation.core.Easing
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.mentalhealthapp.R
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun MoodLineChart(
    moodData: List<MoodEntity>,
    rememberMoodChipIndex: MutableState<String>
) {
    // Sort data by date to ensure chronological order
    val sortedData = remember(moodData, rememberMoodChipIndex.value) {
        when {
            rememberMoodChipIndex.value.isNotEmpty() ->
                moodData.filter { it.mood == rememberMoodChipIndex.value }.sortedBy { it.date }
            else -> moodData.sortedBy { it.date }
        }
    }

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        update = { chart ->
            if (sortedData.isEmpty()) {
                // Handle empty data state
                chart.clear()
                chart.setNoDataText("No mood data available")
                chart.setNoDataTextColor(android.graphics.Color.GRAY)
                chart.setNoDataTextTypeface(android.graphics.Typeface.DEFAULT)
                return@AndroidView
            }

            // Create entries with date as x-value
            val entries = sortedData.mapIndexed { index, moodEntity ->
                // Use the actual date (Long) as x-value, converted to days for better scaling
                val daysSinceFirst = if (sortedData.size > 1) {
                    val firstDate = sortedData.first().date
                    val currentDate = moodEntity.date
                    ((currentDate - firstDate) / (1000 * 60 * 60 * 24)).toFloat() // Convert to days
                } else {
                    0f
                }

                val moodValue = when (moodEntity.mood) {
                    "laughing" -> 5f
                    "smiling" -> 4f
                    "neutral" -> 3f
                    "sad" -> 2f
                    "dead" -> 1f
                    else -> 0f
                }
                Entry(daysSinceFirst, moodValue)
            }

            val dataSet = LineDataSet(entries, "Mood Data").apply {
                color = android.graphics.Color.DKGRAY
                lineWidth = 5f
                circleRadius = 2f
                setCircleColor(android.graphics.Color.TRANSPARENT)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                cubicIntensity = 0.18f
                setDrawFilled(true)
                fillColor = android.graphics.Color.DKGRAY
                fillAlpha = 25
                setDrawCircles(true)
                setDrawValues(false)
            }
            chart.data = LineData(dataSet)
            chart.setDrawBorders(false)
            chart.legend.isEnabled = false
            chart.xAxis.isEnabled = false
            chart.description.isEnabled = false
            chart.animateY(600)
            chart.legend.textSize = 14f
            chart.legend.form = Legend.LegendForm.LINE



            chart.axisRight.isEnabled = false
            chart.axisLeft.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawLabels(true)
                textColor = android.graphics.Color.DKGRAY
                textSize = 14f
                axisMaximum = 5.5f
                axisMinimum = 0f
                granularity = 1f
                setLabelCount(5, false)
            }
            chart.invalidate()
        }
    )
}
