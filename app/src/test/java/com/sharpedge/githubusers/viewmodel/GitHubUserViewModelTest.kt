package com.sharpedge.githubusers.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.sharpedge.githubusers.ui.models.UserDetailsUIModel
import com.sharpedge.githubusers.ui.models.UserSearchResponseUIModel
import com.sharpedge.githubusers.ui.models.UserUIModel
import com.sharpedge.githubusers.ui.state.ErrorType
import com.sharpedge.githubusers.usecase.ISearchUsersUseCase
import com.sharpedge.githubusers.usecase.IUserDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.sharpedge.githubusers.usecase.UserDetailsUseCase
import com.sharpedge.githubusers.usecase.SearchUsersUseCase
import kotlinx.coroutines.test.runTest
import com.sharpedge.githubusers.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class GitHubUserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var searchUsersUseCase: ISearchUsersUseCase
    private lateinit var userDetailsUseCase: IUserDetailsUseCase
    private lateinit var viewModel: GitHubUserViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        searchUsersUseCase = mock() // Using mockito-kotlin mock function
        userDetailsUseCase = mock()
        viewModel = GitHubUserViewModel(searchUsersUseCase, userDetailsUseCase)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `get user details success`() = runTest {
        val username = "sharp-edge"
        val userDetails = UserDetailsUIModel(
            username,
            13657014,
            name = "Sarmad Thebo",
            location = "Dublin",
            publicRepos = 5,
            email = null,
            bio = null,
            followers = 0,
            following = 0,
            avatarUrl = "https://avatars.githubusercontent.com/u/13657014?v=4",
        )
        whenever(userDetailsUseCase.getUserDetails(username)).thenReturn(
            Result.Success(
                userDetails
            )
        )

        viewModel.fetchUserDetails(username)
        delay(2000)
        viewModel.viewState.test {


            val item = awaitItem()
            println("UserDetails in ViewState:"+ item.userDetails)
            assertEquals(userDetails, item.userDetails)
            assertFalse(item.isLoading)
            assertEquals(ErrorType.None, item.error)
        }
    }



    @Test
    fun `search users success`() = runTest {
        val query = "sharp-edge"
        val fakeUser = UserUIModel(
            id = 0,
            login = "sharp-edge",
            avatarUrl = "https://avatars.githubusercontent.com/u/13657014?v=4",
            type = "User"
        )
        val fakeUsers = UserSearchResponseUIModel(
            totalCount = 1,
            items = listOf(fakeUser)
        )
        whenever(searchUsersUseCase.searchUsers(query)).thenReturn(Result.Success(fakeUsers))

        viewModel.searchUsers(query)

        testCoroutineRule.testCoroutineDispatcher.advanceTimeBy(1000)

        viewModel.viewState.test {
            val item = awaitItem()
            val expectedUser = UserUIModel(
                id = 0,
                login = "sharp-edge",
                avatarUrl = "https://avatars.githubusercontent.com/u/13657014?v=4",
                type = "User"
            )
            assertEquals(listOf(expectedUser), item.users)
            assertFalse(item.isLoading)
            assertEquals(ErrorType.None, item.error)
        }
    }



    @Test
    fun `search users failure`() = runTest {
        val query = "sharp-edge"
        whenever(searchUsersUseCase.searchUsers(query)).thenReturn(Result.Failure(ErrorType.NetworkError))

        viewModel.searchUsers(query)

        testCoroutineRule.testCoroutineDispatcher.advanceTimeBy(1000)

        viewModel.viewState.test {
            val item = awaitItem()
            assertTrue(item.users.isEmpty())
            assertFalse(item.isLoading)
            assertEquals(ErrorType.NetworkError, item.error)
        }
    }


    @Test
    fun `get user details failure`() = runTest {
        val username = "sharp-edge"
        whenever(userDetailsUseCase.getUserDetails(username)).thenReturn(Result.Failure(ErrorType.NetworkError))

        viewModel.fetchUserDetails(username)

        testCoroutineRule.testCoroutineDispatcher.advanceTimeBy(2000)

        viewModel.viewState.test {
            val item = awaitItem()
            assertNull(item.userDetails)
            assertFalse(item.isLoading)
            assertEquals(ErrorType.NetworkError, item.error)
        }
    }

}
