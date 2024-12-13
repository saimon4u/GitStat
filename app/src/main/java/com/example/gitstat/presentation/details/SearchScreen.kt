package com.example.gitstat.presentation.details

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.gitstat.presentation.details.components.TopBar
import com.example.gitstat.presentation.utils.ConnectivityObserver
import com.example.gitstat.presentation.utils.NetworkConnectivityObserver

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    pref: SharedPreferences
) {
    val context = LocalContext.current
    val userName = pref.getString("userName", "")
    val connectivityObserver = NetworkConnectivityObserver(context)
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailsState.collectAsState().value


    LaunchedEffect(true) {
        detailsViewModel.onEvent(DetailsEvent.GetUser(userName!!))
    }

    LaunchedEffect(detailsState.isLoading) {
        if(!detailsState.isLoading){
            println("UserName: ${detailsState.searchedUserName} repo: ${detailsState.repos?.size} deployments: ${detailsState.deploymentCount} commit: ${detailsState.commitCount}")
            println(detailsState.usedLanguages)
            println(detailsState.errorMessage)
        }
    }

    Scaffold(
        topBar = {
            TopBar(user = detailsState.user)
        }
    ) { innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Search GitHub Statistics",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Enter a GitHub username to explore their repository",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onBackground.copy(.6f)
            )
            Text(
                text = "stats, contributions, and more!",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onBackground.copy(.6f)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = detailsState.searchedUserName,
                onValueChange = {
                    detailsViewModel.onEvent(DetailsEvent.EnteredUserName(it))
                },
                label = { Text("Username") },
                singleLine = true,
                placeholder = { Text("Enter your username") },
                modifier = modifier.fillMaxWidth(),
                readOnly = detailsState.isLoading,
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    detailsViewModel.onEvent(DetailsEvent.Search)
                },
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            ) {
                if(detailsState.isLoading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }else{
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}