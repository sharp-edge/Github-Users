package com.sharpedge.githubusers.utils

import com.sharpedge.githubusers.ui.state.ErrorType

fun ErrorType.toUserFriendlyMessage(): String {
    return when (this) {
        ErrorType.None -> ""
        ErrorType.NetworkError -> "Please check your network connection."
        ErrorType.NotFoundError -> "The requested resource was not found."
        ErrorType.ServerError -> "The server encountered an error. Please try again later."
        ErrorType.UnknownError -> "An unknown error occurred."
    }
}
