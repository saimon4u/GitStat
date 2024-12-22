package com.example.gitstat.presentation.details

import android.content.SharedPreferences
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import com.example.gitstat.R
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

    LaunchedEffect(detailsState.repos, detailsState.commitList, detailsState.deploymentCount, detailsState.usedLanguages) {
        if(detailsState.repos != null && detailsState.commitList.isNotEmpty() && detailsState.usedLanguages.isNotEmpty())
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

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()


    Scaffold(
        topBar = {
            TopBar(
                user = detailsState.user,
                onProfileClick = {
                    detailsViewModel.onEvent(DetailsEvent.EnteredUserName(detailsState.user?.login!!))
                    detailsViewModel.onEvent(DetailsEvent.Search)
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 8.dp)
    ) { innerPadding->
        if(detailsState.searchedUser != null && !detailsState.isFetchingComplete && detailsState.isLoading){
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column {
                    AsyncImage(
                        model = R.drawable.loading,
                        contentDescription = "Loading GIF",
                        contentScale = ContentScale.Crop,
                        imageLoader = imageLoader,
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Wait! I'm visiting your profile...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
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