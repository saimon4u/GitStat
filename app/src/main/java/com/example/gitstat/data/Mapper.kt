package com.example.gitstat.data

import com.example.gitstat.data.remote.response.UserDto
import com.example.gitstat.domain.model.User

fun UserDto.toUser(): User{
    return User(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl  = avatar_url ?: "",
        bio = bio ?: "",
        publicReposCount = public_repos ?: 0,
        publicGistsCount = public_gists ?: 0,
        followersCount = followers ?: 0,
        followingCount = following ?: 0,
        createdAt = created_at ?: "",
        reposUrl = repos_url ?: "",
        eventsUrl = events_url ?: "",
        htmlUrl = html_url ?: "",
        type = type ?: ""
    )
}