package com.example.gitstat.presentation.utils

import androidx.compose.ui.graphics.Color
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatTimestamp(timestamp: String): String {
    if(timestamp.isEmpty()) return "nil"
    val zonedDateTime = ZonedDateTime.parse(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
    return zonedDateTime.format(formatter)
}

fun formatTimestampIntoMap(timestamp: String): Map<String, String> {
    if (timestamp.isEmpty()) return mapOf("error" to "nil")
    val zonedDateTime = ZonedDateTime.parse(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
    val formattedDate = zonedDateTime.format(formatter)
    val day = zonedDateTime.dayOfMonth.toString()
    val month = zonedDateTime.month.toString()
    val year = zonedDateTime.year.toString()
    return mapOf("day" to day, "month" to month, "year" to year)
}


val pieChartColors = listOf(
    Color(0xFFFF0000), Color(0xFFFFA500), Color(0xFFFFFF00), Color(0xFF008000), Color(0xFF0000FF),
    Color(0xFF4B0082), Color(0xFFEE82EE), Color(0xFFA52A2A), Color(0xFF5F9EA0), Color(0xFF7FFF00),
    Color(0xFFD2691E), Color(0xFFFF7F50), Color(0xFF6495ED), Color(0xFFFFD700), Color(0xFF008080),
    Color(0xFF00FFFF), Color(0xFF00008B), Color(0xFF8A2BE2), Color(0xFF00FF00), Color(0xFF8B0000)
)

val months = listOf("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")