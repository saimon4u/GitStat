package com.example.gitstat.presentation.details

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gitstat.presentation.details.components.DetailsBox
import com.example.gitstat.presentation.details.components.SearchBox
import com.example.gitstat.presentation.details.components.TopBar
import com.example.gitstat.presentation.onBoard.OnBoardEvent
import com.example.gitstat.presentation.utils.ConnectivityObserver
import com.example.gitstat.presentation.utils.NetworkConnectivityObserver

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    pref: SharedPreferences
) {
    val userName = pref.getString("userName", "")
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsState().value


    LaunchedEffect(Unit) {
        if(detailsState.user == null)
            detailsViewModel.onEvent(DetailsEvent.GetUser(userName!!))
    }
    LaunchedEffect(detailsState.repos, detailsState.commitCount, detailsState.deploymentCount, detailsState.usedLanguages) {
        println(
            "Repos: ${detailsState.repos?.size}, " +
                    "Commits: ${detailsState.commitCount}, " +
                    "Deployments: ${detailsState.deploymentCount}, " +
                    "Languages: ${detailsState.usedLanguages}"
        )
    }


    Scaffold(
        topBar = {
            TopBar(user = detailsState.user)
        }
    ) { innerPadding->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ){
            if(detailsState.isFetchingComplete){
                DetailsBox(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    user = detailsState.searchedUser
                )
            }else{
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