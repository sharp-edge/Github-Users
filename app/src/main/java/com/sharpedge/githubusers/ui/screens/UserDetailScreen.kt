package com.sharpedge.githubusers.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.sharpedge.githubusers.viewmodel.GitHubUserViewModel
import com.sharpedge.githubusers.R


@Composable
fun UserProfile(viewModel: GitHubUserViewModel,) {

    val viewState by viewModel.viewState.collectAsState()
    val user = viewState.userDetails
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberImagePainter(data = user?.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(8.dp))


        user?.name?.let { name ->
            Text(name, fontSize = 24.sp)
        }


        Spacer(modifier = Modifier.height(4.dp))

        // Username
        Text("@" + user?.login, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(8.dp))

        // Bio
        user?.bio?.let { bio ->
            Text(bio, fontSize = 14.sp)
        }


        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Followers, Following, Repo count
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("👥 ${user?.followers} followers")
            Text("👣 ${user?.following} following")
            Text("✨ ${user?.publicRepos}")
        }

        Spacer(modifier = Modifier.height(16.dp))

//        // Company, Location, Email
//        user.company?.let {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(painterResource(id = R.drawable.ic_company), contentDescription = null)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(it)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))

        user?.location?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.ic_location), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(it)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        user?.email?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.ic_email), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(it)
            }
        }
    }
}
