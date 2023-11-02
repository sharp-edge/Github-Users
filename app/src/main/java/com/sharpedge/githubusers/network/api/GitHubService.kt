package com.sharpedge.githubusers.network.api

import com.sharpedge.githubusers.model.UserDetails
import com.sharpedge.githubusers.model.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") query: String): Response<UserSearchResponse>

    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): Response<UserDetails>

}