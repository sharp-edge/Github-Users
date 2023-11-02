package com.sharpedge.githubusers.repository
import com.sharpedge.githubusers.model.UserDetails
import com.sharpedge.githubusers.model.UserSearchResponse
import com.sharpedge.githubusers.network.api.GitHubService
import com.sharpedge.githubusers.utils.*

class GitHubUserRepository(private val gitHubService: GitHubService): IGitHubUserRepository {


    override suspend fun searchUsers(query: String): UserSearchResponse {
        return gitHubService.searchUsers(query).processResponse()
    }

    override suspend fun getUserDetails(username: String): UserDetails {
        return gitHubService.getUserDetails(username).processResponse()
    }

}

