package com.example.gitstat.domain.repository

import com.example.gitstat.domain.model.User
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertUser(user: User)
    suspend fun getUser(
        forceFetchFromRemote: Boolean,
        userName: String
    ): Flow<Resource<User>>
}