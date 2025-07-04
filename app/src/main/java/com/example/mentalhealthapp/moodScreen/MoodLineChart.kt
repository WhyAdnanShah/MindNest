package com.example.mentalhealthapp.moodScreen

import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mentalhealthapp.moodROOMdatabase.MoodEntity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet



@Composable
fun MoodLineChart(
    moodData: List<MoodEntity>
){
    AndroidView(
        factory = {
            context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )


            }
        },
        modifier = Modifier.wrapContentSize().padding(vertical = 16.dp),
        update = {
            chart ->
            val entries = moodData.mapIndexed { index, moodEntity ->
                val moodValue = when (moodEntity.mood) {
                    "laughing" -> 5f
                    "smiling" -> 4f
                    "neutral" -> 3f
                    "sad" -> 2f
                    "dead" -> 1f
                    else -> 0f
                }
                Entry(index.toFloat(), moodValue)
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