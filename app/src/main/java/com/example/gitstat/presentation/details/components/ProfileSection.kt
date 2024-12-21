package com.example.gitstat.presentation.details.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.example.gitstat.presentation.utils.formatTimestamp

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    user: User? = null
) {
    val context = LocalContext.current
    val imgState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(user?.avatarUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(imgState is AsyncImagePainter.State.Error){
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = CircleShape
                    ),
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
                    .size(100.dp)
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = user?.login ?: "No name",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier
                    .size(24.dp),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user?.htmlUrl))
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = "Link to the account"
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Since ${formatTimestamp(user?.createdAt ?: "")}",
            style = MaterialTheme.typography.bodySmall,
            fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
            fontWeight = FontWeight.Thin,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
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
        if(user?.bio != null){
            Text(
                text = user.bio,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}