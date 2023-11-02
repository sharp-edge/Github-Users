package com.sharpedge.githubusers.di
import com.sharpedge.githubusers.network.ApiClient
import com.sharpedge.githubusers.repository.GitHubUserRepository
import com.sharpedge.githubusers.repository.IGitHubUserRepository
import com.sharpedge.githubusers.usecase.ISearchUsersUseCase
import com.sharpedge.githubusers.usecase.IUserDetailsUseCase
import com.sharpedge.githubusers.usecase.UserDetailsUseCase
import com.sharpedge.githubusers.usecase.SearchUsersUseCase
import com.sharpedge.githubusers.viewmodel.GitHubUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Provide GitHubService
    single { ApiClient.provideGitHubService() }

    // Provide GitHubUserRepository
    single<IGitHubUserRepository> { GitHubUserRepository(get()) }

    // Provide SearchUsersUseCase
    single<IUserDetailsUseCase> { UserDetailsUseCase(get()) }

    single<ISearchUsersUseCase> { SearchUsersUseCase(get()) }


    // Provide ViewModel
    viewModel { GitHubUserViewModel(get(), get()) }
}