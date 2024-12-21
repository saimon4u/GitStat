package com.example.gitstat.presentation.utils

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.gitstat.presentation.details.SearchScreen
import com.example.gitstat.presentation.onBoard.OnBoardingScreen
import com.example.gitstat.presentation.onBoard.SplashScreen

@Composable
fun NavigationHelper(
    navController: NavHostController,
    pref: SharedPreferences
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen
    ){
        composable<Screen.SplashScreen>{
            SplashScreen(
                navController = navController,
                pref = pref
            )
        }
        composable<Screen.OnBoardingScreen> {
            OnBoardingScreen(navController = navController, pref = pref)
        }
        composable<Screen.SearchScreen> {
            SearchScreen(
                pref = pref,
                navController = navController
            )
        }
    }
}