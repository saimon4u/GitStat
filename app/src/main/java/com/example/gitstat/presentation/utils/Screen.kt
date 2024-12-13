package com.example.gitstat.presentation.utils

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen{
    @Serializable
    data object OnBoardingScreen: Screen
    @Serializable
    data object SearchScreen: Screen
    @Serializable
    data object SplashScreen: Screen
}