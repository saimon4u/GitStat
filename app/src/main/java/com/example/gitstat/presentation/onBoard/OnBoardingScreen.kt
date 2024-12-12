package com.example.gitstat.presentation.onBoard

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gitstat.presentation.utils.Screen

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val onBoardViewModel = hiltViewModel<OnBoardViewModel>()
    val onBoardState = onBoardViewModel.onBoardState.collectAsState().value

    if(onBoardState.errorMessage.isNotBlank()){
        Toast.makeText(context, onBoardState.errorMessage, Toast.LENGTH_SHORT).show()
        onBoardViewModel.onEvent(OnBoardEvent.ResetErrorMessage)
    }
    if(onBoardState.navigate){
        navController.navigate(Screen.HomeScreen)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Welcome to GitStat",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "It seems like you are new here,",
            style = MaterialTheme.typography.bodySmall,
            fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
            fontWeight = FontWeight.Thin,
            color = MaterialTheme.colorScheme.onBackground.copy(.6f)
        )
        Text(
            text = "please enter your username to get started",
            style = MaterialTheme.typography.bodySmall,
            fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
            fontWeight = FontWeight.Thin,
            color = MaterialTheme.colorScheme.onBackground.copy(.6f)
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = onBoardState.userName,
            onValueChange = {
                onBoardViewModel.onEvent(OnBoardEvent.EnteredUserName(it))
            },
            label = { Text("Username") },
            singleLine = true,
            placeholder = { Text("Enter your username") },
            modifier = modifier.fillMaxWidth(),
            readOnly = onBoardState.isLoading,
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                onBoardViewModel.onEvent(OnBoardEvent.GetStarted)
            },
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
        ) {
            if(onBoardState.isLoading){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }else{
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}