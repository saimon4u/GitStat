package com.example.gitstat.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gitstat.presentation.details.DetailsState
import com.example.gitstat.presentation.utils.formatTimestampIntoMap

@Composable
fun DetailsBox(
    modifier: Modifier = Modifier,
    detailsState: DetailsState
) {
    var commitTimeList by remember { mutableStateOf(emptyList<CommitTime>()) }
    var yearMap by remember { mutableStateOf(emptyMap<String, List<CommitTime>>()) }

    LaunchedEffect(detailsState.commitList) {
        commitTimeList = detailsState.commitList.map {
            val formattedTimestamp = formatTimestampIntoMap(it.date)
            CommitTime(
                id = it.id,
                day = formattedTimestamp["day"] ?: "nil",
                month = formattedTimestamp["month"] ?: "nil",
                year = formattedTimestamp["year"] ?: "nil"
            )
        }
        yearMap = commitTimeList.groupBy { it.year }.toSortedMap()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileSection(user = detailsState.searchedUser)
        Spacer(Modifier.height(16.dp))
        StatisticsSection(
            modifier = Modifier.fillMaxWidth(),
            repoCount = detailsState.repos?.size ?: 0,
            commitCount = detailsState.commitList.size,
            deploymentCount = detailsState.deploymentCount,
            languageCount = detailsState.usedLanguages.size
        )
        Spacer(Modifier.height(16.dp))
        LanguagePieChart(
            languages = detailsState.usedLanguages,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Commit Graph",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(16.dp))
        yearMap.forEach { (key, value) ->
            CommitGraph(
                commitList = value,
                year = key
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}