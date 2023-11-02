package com.sharpedge.githubusers.utils


import retrofit2.Response
import retrofit2.HttpException

fun <T> Response<T>.processResponse(): T {
    if (this.isSuccessful && this.body() != null) {
        return this.body()!!
    } else {
        throw HttpException(this)
    }
}
