package com.sharpedge.githubusers.ui.models

data class UserDetailsUIModel(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val name: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
)

