package com.example.gitstat.presentation.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.gitstat.R
import com.example.gitstat.domain.model.User

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    user: User?,
    onProfileClick: () -> Unit
) {

    val imgState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(user?.avatarUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
            .height(60.dp)
        ,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(imgState is AsyncImagePainter.State.Error){
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .clickable {
                        onProfileClick()
                    },
                painter = painterResource(
                    id = R.drawable.logo
                ),
                contentDescription = "No avatar found!",
                contentScale = ContentScale.Crop
            )
        }
        if(imgState is AsyncImagePainter.State.Success){
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = CircleShape
                    )
                    .clickable {
                        onProfileClick()
                    },
                painter = imgState.painter,
                contentDescription = "Github avatar",
                contentScale = ContentScale.Crop
            )
        }
        Column (
            modifier = modifier
                .fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = user?.login ?: "No Name",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
            Row {
                Text(
                    text = "Followers: ${user?.followersCount ?: 0}",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                    color = MaterialTheme.colorScheme.onBackground.copy(.6f),
                    fontWeight = FontWeight.Thin
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Following: ${user?.followingCount ?: 0}",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                    color = MaterialTheme.colorScheme.onBackground.copy(.6f),
                    fontWeight = FontWeight.Thin
                )
            }
        }
    }
}