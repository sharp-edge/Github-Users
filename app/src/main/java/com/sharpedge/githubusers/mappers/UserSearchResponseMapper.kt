package com.sharpedge.githubusers.mappers

import com.sharpedge.githubusers.model.UserSearchResponse
import com.sharpedge.githubusers.ui.models.UserSearchResponseUIModel

fun UserSearchResponse.toUIModel(): UserSearchResponseUIModel {
    return UserSearchResponseUIModel(
        totalCount = totalCount,
        items = items.map { it.toUIModel() }
    )
}