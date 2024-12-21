package com.example.gitstat.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.gitstat.presentation.utils.pieChartColors

@Composable
fun LanguagePieChart(
    modifier: Modifier = Modifier,
    languages: Map<String, Int>,
) {
    val pieChartData = PieChartData(
        slices = languages.map { (language, count) ->
            PieChartData.Slice(
                value = count.toFloat(),
                label = language,
                color = pieChartColors[languages.keys.indexOf(language) % pieChartColors.size]
            )
        },
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        sliceLabelTextColor = Color.Black,
        sliceLabelTextSize = 14.sp,
        animationDuration = 1000,
        backgroundColor = MaterialTheme.colorScheme.background,
        strokeWidth = 120f,
        activeSliceAlpha = .9f,
    )

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Language Used:",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PieChart(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                pieChartData,
                pieChartConfig
            )
        }
        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            columns = GridCells.Adaptive(150.dp),
            content = {
                items(pieChartData.slices){
                    Row {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(it.color)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "${it.label} - ${"%.2f".format((it.value * 100) / languages.values.sum())}%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        )
    }
}