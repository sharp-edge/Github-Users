package com.sharpedge.githubusers.ui.state

import com.sharpedge.githubusers.ui.models.UserDetailsUIModel
import com.sharpedge.githubusers.ui.models.UserUIModel

data class ViewState(
    val users: List<UserUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorType = ErrorType.None,
    val selectedUser: UserUIModel? = null,
    val userDetails: UserDetailsUIModel? = null
)