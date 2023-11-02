package com.sharpedge.githubusers.ui.models

data class UserSearchResponseUIModel(
    val totalCount: Int,
    val items: List<UserUIModel>
)

