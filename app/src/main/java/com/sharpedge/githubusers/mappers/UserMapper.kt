package com.sharpedge.githubusers.mappers

import com.sharpedge.githubusers.model.User
import com.sharpedge.githubusers.ui.models.UserUIModel

fun User.toUIModel(): UserUIModel {
    return UserUIModel(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        type = type ?: ""
    )
}