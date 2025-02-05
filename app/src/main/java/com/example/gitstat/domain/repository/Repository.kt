package com.example.gitstat.domain.repository

import com.example.gitstat.domain.model.Commit
import com.example.gitstat.domain.model.Repo
import com.example.gitstat.domain.model.User
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertUser(user: User)

    suspend fun getUser(
        forceFetchFromRemote: Boolean,
        userName: String
    ): Flow<Resource<User>>

    suspend fun getRepos(
        userName: String
    ): Flow<Resource<List<Repo>>>

    suspend fun getLanguages(
        userName: String,
        repoName: String
    ): Flow<Resource<Map<String, Int>>>

    suspend fun getDeployments(
        userName: String,
        repoName: String
    ): Flow<Resource<Int>>

    suspend fun getCommits(
        userName: String,
        repoName: String
    ): Flow<Resource<List<Commit>>>
}