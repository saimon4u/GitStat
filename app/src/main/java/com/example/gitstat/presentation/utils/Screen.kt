package com.example.gitstat.presentation.utils

@kotlinx.serialization.Serializable
sealed class Screen{
    @kotlinx.serialization.Serializable
    data object OnBoardingScreen: Screen()
    @kotlinx.serialization.Serializable
    data object HomeScreen: Screen()
    @kotlinx.serialization.Serializable
    data object SplashScreen: Screen()
}