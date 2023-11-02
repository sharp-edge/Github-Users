package com.sharpedge.githubusers.network

import com.sharpedge.githubusers.network.api.GitHubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


    private const val BASE_URL = "https://api.github.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun provideGitHubService(): GitHubService {
        return gitHubService
    }

   private val gitHubService: GitHubService = retrofit.create(GitHubService::class.java)
}