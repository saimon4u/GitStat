package com.example.gitstat.presentation.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.gitstat.R
import com.example.gitstat.domain.model.User

@Composable
fun DetailsBox(
    modifier: Modifier = Modifier,
    user: User? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val imgState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user?.avatarUrl)
                .size(Size.ORIGINAL)
                .build()
        ).state

        if(imgState is AsyncImagePainter.State.Error){
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
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
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = CircleShape
                    ),
                painter = imgState.painter,
                contentDescription = "Github avatar",
                contentScale = ContentScale.Crop
            )
        }
    }
}