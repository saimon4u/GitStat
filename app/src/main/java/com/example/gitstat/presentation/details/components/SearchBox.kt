package com.example.gitstat.presentation.details.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    userName: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = modifier
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
            value = userName,
            onValueChange = onValueChange,
            label = { Text("Username") },
            singleLine = true,
            placeholder = { Text("Enter your username") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = readOnly,
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onSearchClick,
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
        ) {
            if(readOnly){
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