package com.example.gitstat.presentation.utils

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Serializable
sealed interface Screen{
    @Serializable
    data object OnBoardingScreen: Screen
    @Serializable
    data object SearchScreen: Screen
    @Serializable
    data object SplashScreen: Screen
}

fun formatTimestamp(timestamp: String): String {
    if(timestamp.isEmpty()) return "nil"
    val zonedDateTime = ZonedDateTime.parse(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
    return zonedDateTime.format(formatter)
}


val pieChartColors = listOf(
    Color(0xFFFF0000), Color(0xFFFFA500), Color(0xFFFFFF00), Color(0xFF008000), Color(0xFF0000FF),
    Color(0xFF4B0082), Color(0xFFEE82EE), Color(0xFFA52A2A), Color(0xFF5F9EA0), Color(0xFF7FFF00),
    Color(0xFFD2691E), Color(0xFFFF7F50), Color(0xFF6495ED), Color(0xFFFFD700), Color(0xFF008080),
    Color(0xFF00FFFF), Color(0xFF00008B), Color(0xFF8A2BE2), Color(0xFF00FF00), Color(0xFF8B0000)
)