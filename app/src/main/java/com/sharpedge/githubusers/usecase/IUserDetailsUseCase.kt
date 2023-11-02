package com.sharpedge.githubusers.usecase

import com.sharpedge.githubusers.ui.models.UserDetailsUIModel
import com.sharpedge.githubusers.utils.Result

interface IUserDetailsUseCase {

    suspend fun getUserDetails(username: String): Result<UserDetailsUIModel>
}