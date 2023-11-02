package com.sharpedge.githubusers.utils

import com.sharpedge.githubusers.ui.state.ErrorType

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val errorType: ErrorType) : Result<Nothing>()
}
