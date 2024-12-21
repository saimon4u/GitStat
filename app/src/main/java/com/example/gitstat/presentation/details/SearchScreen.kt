package com.example.gitstat.presentation.details

import android.content.SharedPreferences
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gitstat.presentation.details.components.DetailsBox
import com.example.gitstat.presentation.details.components.SearchBox
import com.example.gitstat.presentation.details.components.TopBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    pref: SharedPreferences,
    navController: NavController
) {
    val userName = pref.getString("userName", "")
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsState().value


    LaunchedEffect(Unit) {
        if(detailsState.user == null)
            detailsViewModel.onEvent(DetailsEvent.GetUser(userName!!))
    }

    LaunchedEffect(detailsState.repos, detailsState.commitCount, detailsState.deploymentCount, detailsState.usedLanguages) {
        if(detailsState.repos != null && detailsState.commitCount != 0 && detailsState.usedLanguages.isNotEmpty())
            detailsViewModel.onEvent(DetailsEvent.FetchComplete)
    }

    LaunchedEffect(detailsState.repos) {
        if(detailsState.repos != null && !detailsState.isFetchingComplete)
            detailsViewModel.onEvent(DetailsEvent.FetchStatistics)
    }

    BackHandler {
        if(detailsState.isFetchingComplete)
            detailsViewModel.onEvent(DetailsEvent.ResetState)
        else
            navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopBar(user = detailsState.user)
        },
    ) { innerPadding->
        if(detailsState.searchedUser != null && !detailsState.isFetchingComplete){
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        else {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (detailsState.isFetchingComplete) {
                    DetailsBox(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        detailsState = detailsState
                    )
                } else {
                    SearchBox(
                        modifier
                            .fillMaxSize(1f)
                            .padding(innerPadding),
                        userName = detailsState.searchedUserName,
                        readOnly = detailsState.isLoading,
                        onValueChange = {
                            detailsViewModel.onEvent(DetailsEvent.EnteredUserName(it))
                        },
                        onSearchClick = {
                            detailsViewModel.onEvent(DetailsEvent.Search)
                        }
                    )
                }
            }
        }
    }
}