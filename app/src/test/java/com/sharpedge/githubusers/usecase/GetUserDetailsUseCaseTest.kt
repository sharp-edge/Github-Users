package com.sharpedge.githubusers.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sharpedge.githubusers.mappers.toUIModel
import com.sharpedge.githubusers.model.UserDetails
import com.sharpedge.githubusers.repository.GitHubUserRepository
import com.sharpedge.githubusers.repository.IGitHubUserRepository
import com.sharpedge.githubusers.ui.state.ErrorType
import com.sharpedge.githubusers.viewmodel.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import com.sharpedge.githubusers.utils.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.mockito.kotlin.doAnswer
import retrofit2.HttpException


@ExperimentalCoroutinesApi
class GetUserDetailsUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: IGitHubUserRepository
    private lateinit var useCase: IUserDetailsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mock() // Using mockito-kotlin mock function
        useCase = UserDetailsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get user details success`() = runTest {
        val username = "sharp-edge"
        val userDetails = generateCorrectUserDetails(username)
        whenever(repository.getUserDetails(username)).thenReturn(userDetails)

        val result = useCase.getUserDetails(username)

        assertTrue(result is Result.Success)
        assertEquals(userDetails.toUIModel(), (result as Result.Success).data)
    }

    @Test
    fun `get user details network error`() = runTest {
        val username = "sharp-edge"
        whenever(repository.getUserDetails(username)).doAnswer {
            throw IOException()
        }

        val result = useCase.getUserDetails(username)

        assertTrue(result is Result.Failure)
        assertEquals(ErrorType.NetworkError, (result as Result.Failure).errorType)
    }

    @Test
    fun `get user details not found error`() = runTest {
        val username = "sharp-edge"
        val httpException = HttpException(Response.error<Any>(404, "Not Found".toResponseBody()))

        whenever(repository.getUserDetails(username)).doAnswer {
            throw httpException
        }

        val result = useCase.getUserDetails(username)

        assertTrue(result is Result.Failure)
        assertEquals(ErrorType.NotFoundError, (result as Result.Failure).errorType)
    }

    @Test
    fun `get user details server error`() = runTest {
        val username = "sharp-edge"
        val httpException = HttpException(Response.error<Any>(500, "Server Error".toResponseBody()))

        whenever(repository.getUserDetails(username)).doAnswer {
            throw httpException
        }

        val result = useCase.getUserDetails(username)

        assertTrue(result is Result.Failure)
        assertEquals(ErrorType.ServerError, (result as Result.Failure).errorType)
    }

    @Test
    fun `get user details unknown error`() = runTest {
        val username = "sharp-edge"

        whenever(repository.getUserDetails(username)).doAnswer {
            throw RuntimeException()
        }

        val result = useCase.getUserDetails(username)

        assertTrue(result is Result.Failure)
        assertEquals(ErrorType.UnknownError, (result as Result.Failure).errorType)
    }






    private fun generateCorrectUserDetails(username: String) : UserDetails {
        return UserDetails(
            login = username,
            id= 13657014,
            nodeId = "MDQ6VXNlcjEzNjU3MDE0",
            name = "Sarmad Thebo",
            location = "Dublin",
            publicRepos = 5,
            email = null,
            bio =  "\"Passionate coder 🚀 | Software Developer 📱 | Building digital experiences one line of code at a time 💻 | Open-source enthusiast 🌟 | Forever learning.",
            url = "https://api.github.com/users/sharp-edge",
            followers = 0,
            following = 0,
            gravatarId = "",
            followersUrl =  "https://api.github.com/users/sharp-edge/followers",
            followingUrl = "https://api.github.com/users/sharp-edge/following{/other_user}",
            gistsUrl = "https://api.github.com/users/sharp-edge/gists{/gist_id}",
            htmlUrl = "https://github.com/sharp-edge",
            starredUrl = "https://api.github.com/users/sharp-edge/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/sharp-edge/subscriptions",
            organizationsUrl = "https://api.github.com/users/sharp-edge/orgs",
            reposUrl = "https://api.github.com/users/sharp-edge/repos",
            eventsUrl = "https://api.github.com/users/sharp-edge/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/sharp-edge/received_events",
            type = "User",
            siteAdmin = false,
            hireable = true,
            twitterUsername = null,
            publicGists = 0,
            createdAt = "2015-08-05T08:38:33Z",
            updatedAt = "2023-10-06T00:03:45Z",
            blog = "https://www.linkedin.com/in/sarmadthebo",
            company = null,
            avatarUrl = "https://avatars.githubusercontent.com/u/13657014?v=4",
        )
    }
}
