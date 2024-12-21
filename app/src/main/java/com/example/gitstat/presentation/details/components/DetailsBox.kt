package com.example.gitstat.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import com.example.gitstat.presentation.details.DetailsState

@Composable
fun DetailsBox(
    modifier: Modifier = Modifier,
    detailsState: DetailsState
) {
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
            commitCount = detailsState.commitCount,
            deploymentCount = detailsState.deploymentCount,
            languageCount = detailsState.usedLanguages.size
        )
        Spacer(Modifier.height(16.dp))
        LanguagePieChart(
            languages = detailsState.usedLanguages,
        )
    }
}