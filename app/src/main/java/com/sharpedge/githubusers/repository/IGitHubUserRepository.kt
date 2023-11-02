package com.sharpedge.githubusers.repository

import com.sharpedge.githubusers.model.UserDetails
import com.sharpedge.githubusers.model.UserSearchResponse

interface IGitHubUserRepository {

    suspend fun searchUsers(query: String): UserSearchResponse
    suspend fun getUserDetails(username: String): UserDetails


}