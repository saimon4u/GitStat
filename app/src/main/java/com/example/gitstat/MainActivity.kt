package com.example.gitstat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.gitstat.presentation.utils.NavigationHelper
import com.example.gitstat.ui.theme.GitStatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitStatTheme {
                val pref = this.getSharedPreferences("gitstat", MODE_PRIVATE)
                val navController = rememberNavController()
                NavigationHelper(
                    navController = navController,
                    pref = pref
                )
            }
        }
    }
}