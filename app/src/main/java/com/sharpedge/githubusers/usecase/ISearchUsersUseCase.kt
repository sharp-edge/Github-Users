package com.sharpedge.githubusers.usecase

import com.sharpedge.githubusers.ui.models.UserSearchResponseUIModel
import com.sharpedge.githubusers.utils.Result

interface ISearchUsersUseCase {

    suspend fun searchUsers(query: String): Result<UserSearchResponseUIModel>
}