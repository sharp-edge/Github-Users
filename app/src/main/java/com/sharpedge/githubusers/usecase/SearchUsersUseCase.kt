package com.sharpedge.githubusers.usecase

import com.sharpedge.githubusers.mappers.toUIModel
import com.sharpedge.githubusers.repository.IGitHubUserRepository
import com.sharpedge.githubusers.ui.models.UserSearchResponseUIModel
import com.sharpedge.githubusers.ui.state.ErrorType
import retrofit2.HttpException
import java.io.IOException
import com.sharpedge.githubusers.utils.Result

class SearchUsersUseCase(private val repository: IGitHubUserRepository) : ISearchUsersUseCase {

    override suspend fun searchUsers(query: String): Result<UserSearchResponseUIModel> {
        return try {
            val response = repository.searchUsers(query)
            Result.Success(response.toUIModel())
        } catch (exception: IOException) {

            Result.Failure(ErrorType.NetworkError)
        } catch (exception: HttpException) {

            when (exception.code()) {
                in 400..499 -> Result.Failure(ErrorType.NotFoundError)
                in 500..599 -> Result.Failure(ErrorType.ServerError)
                else -> Result.Failure(ErrorType.UnknownError)
            }
        } catch (exception: Exception) {

            Result.Failure(ErrorType.UnknownError)
        }
    }
}