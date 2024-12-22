package com.example.gitstat.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.gitstat.domain.model.Commit
import com.example.gitstat.presentation.utils.months

@Composable
fun CommitGraph(
    modifier: Modifier = Modifier,
    commitList: List<CommitTime>,
    year: String,
) {

    var monthMap by remember { mutableStateOf(emptyMap<String, List<CommitTime>>()) }
    val xStepSize by remember { mutableIntStateOf(12) }
    val yStepSize by remember { mutableIntStateOf(10) }
    var points by remember { mutableStateOf(emptyList<Point>()) }

    LaunchedEffect(commitList) {
        if(commitList.isNotEmpty()){
            monthMap = commitList.groupBy { it.month }.toMutableMap().apply {
                months.forEach { month ->
                    if (!containsKey(month)) {
                        this[month] = emptyList()
                    }
                }
            }
            points = monthMap.keys.map {
                Point(
                    x = (months.indexOf(it)).toFloat(),
                    y = monthMap[it]?.size?.toFloat() ?: 0f
                )
            }.sortedBy { it.x }
            println(points)
        }
    }
    val xAxisData = AxisData.Builder()
        .steps(xStepSize)
        .axisStepSize(50.dp)
        .backgroundColor(Color.Transparent)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisOffset(50.dp)
        .labelData { i ->
            if(i < 12){
                months[i].substring(0, 3)
            }else{
                "DEC"
            }
        }
        .labelAndAxisLinePadding(10.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .axisStepSize(30.dp)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .labelData { i ->
            val yScale = 50 / yStepSize
            (i * yScale).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = points,
                    LineStyle(
                        lineType = LineType.SmoothCurve(isDotted = true),
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        color = Color.Blue
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = MaterialTheme.colorScheme.background
    )
    if(points.isNotEmpty()){
        Column {
            Text(
                text = "Year : $year",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(8.dp))
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                lineChartData = lineChartData
            )
        }
    }
}