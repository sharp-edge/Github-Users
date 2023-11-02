package com.sharpedge.githubusers.mappers

import com.sharpedge.githubusers.model.UserDetails
import com.sharpedge.githubusers.ui.models.UserDetailsUIModel

fun UserDetails.toUIModel(): UserDetailsUIModel {
    return UserDetailsUIModel(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        location = location,
        email = email,
        bio = bio,
        publicRepos = publicRepos,
        followers = followers,
        following = following
    )
}