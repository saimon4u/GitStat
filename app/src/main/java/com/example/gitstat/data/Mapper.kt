package com.example.gitstat.data

import com.example.gitstat.data.remote.response.CommitDto
import com.example.gitstat.data.remote.response.RepoDto
import com.example.gitstat.data.remote.response.UserDto
import com.example.gitstat.domain.model.Commit
import com.example.gitstat.domain.model.Repo
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

fun RepoDto.toRepo(): Repo{
    return Repo(
        name = name ?: "",
        isPrivate = private ?: false,
        repoLink = html_url ?: "",
        forksCount = forks_count ?: 0,
        eventsUrl = events_url ?: "",
        languagesUrl = languages_url ?: "",
        commitsUrl = commits_url ?: "",
        deploymentsUrl = deployments_url ?: "",
        size = size ?: 0,
        watchersCount = watchers ?: 0
    )
}

fun CommitDto.toCommit(): Commit{
    return Commit(
        id = node_id ?: "",
        date = commit?.author?.date ?: "",
        message = commit?.message ?: ""
    )
}