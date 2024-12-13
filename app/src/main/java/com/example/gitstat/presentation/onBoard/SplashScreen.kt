package com.example.gitstat.presentation.onBoard

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gitstat.R
import com.example.gitstat.presentation.utils.ConnectivityObserver
import com.example.gitstat.presentation.utils.NetworkConnectivityObserver
import com.example.gitstat.presentation.utils.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    pref: SharedPreferences
) {
    val context = LocalContext.current
    val userName = pref.getString("userName", "")
    val connectivityObserver = NetworkConnectivityObserver(context)
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )
    LaunchedEffect(status) {
        delay(2000)
        if(status == ConnectivityObserver.Status.Available){
            navController.popBackStack()
            if(userName?.isNotBlank() == true){
                navController.navigate(Screen.SearchScreen)
            }else{
                navController.navigate(Screen.OnBoardingScreen)
            }
        }else{
            Toast.makeText(context, "Please check your internet connection...", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = "GitStat Logo",
            modifier = modifier
                .size(200.dp),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "GitStat",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}