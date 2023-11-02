package com.sharpedge.githubusers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharpedge.githubusers.ui.models.UserUIModel
import com.sharpedge.githubusers.ui.state.ErrorType
import com.sharpedge.githubusers.ui.state.ViewState
import com.sharpedge.githubusers.usecase.ISearchUsersUseCase
import com.sharpedge.githubusers.usecase.IUserDetailsUseCase
import com.sharpedge.githubusers.usecase.UserDetailsUseCase
import com.sharpedge.githubusers.usecase.SearchUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.sharpedge.githubusers.utils.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow

class GitHubUserViewModel(
    private val searchUsersUseCase: ISearchUsersUseCase,
    private val userDetailsUseCase: IUserDetailsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    private val _navigationEvents = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvents = _navigationEvents.asStateFlow()

    private val debouncePeriod: Long = 1000 // 1 second debounce period
    private var searchJob: Job? = null

    fun searchUsers(query: String) {

        searchJob?.cancel()

        if(query.length >= 2) {
            searchJob = viewModelScope.launch {
                delay(debouncePeriod)
                _viewState.value = _viewState.value.copy(isLoading = true, error = ErrorType.None)

                when (val result = searchUsersUseCase.searchUsers(query)) {
                    is Result.Success -> {
                        val usersList = result.data.items
                        _viewState.value =
                            _viewState.value.copy(users = usersList, isLoading = false)
                    }

                    is Result.Failure -> {
                        _viewState.value =
                            _viewState.value.copy(isLoading = false, error = result.errorType)
                    }
                }


            }
        }
    }


    fun fetchUserDetails(username: String) {
        _viewState.value = _viewState.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = userDetailsUseCase.getUserDetails(username)) {
                is Result.Success -> {
                    navigateToUserDetail()
                    _viewState.value = _viewState.value.copy(isLoading = false, userDetails = result.data)
                }
                is Result.Failure -> {
                    _viewState.value = _viewState.value.copy(isLoading = false, error = result.errorType)
                }
            }
        }
    }


    fun selectUser(user: UserUIModel) {
       // _selectedUser.value = user
        _viewState.value = _viewState.value.copy(selectedUser = user)
        user.login?.let {login ->
            fetchUserDetails(login)
        }

    }



    sealed class NavigationEvent {
        object NavigateToUserDetail : NavigationEvent()
    }



    fun navigateToUserDetail() {
        _navigationEvents.value = NavigationEvent.NavigateToUserDetail
    }

    fun resetNavigationEvent() {
        _navigationEvents.value = null
    }

}
