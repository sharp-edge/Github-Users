package com.sharpedge.githubusers.ui.others

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.sharpedge.githubusers.R
import com.sharpedge.githubusers.ui.models.UserUIModel

@Composable
fun UserListItem(user: UserUIModel, onUserClicked: (UserUIModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
            .clickable { onUserClicked(user) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter =
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = user.avatarUrl)
                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                            placeholder(R.mipmap.ic_launcher_round)
                            transformations(CircleCropTransformation())
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                user.login?.let { login ->
                    Text(text = login, fontWeight = FontWeight.Bold)
                }


                //Text(text = user.id, fontStyle = FontStyle.Italic)
                //Text(text = ""+user.score, fontStyle = FontStyle.Italic)
            }

            //Text(text = user.score)
        }
    }
}