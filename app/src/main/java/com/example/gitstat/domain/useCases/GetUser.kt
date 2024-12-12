package com.example.gitstat.domain.useCases

import com.example.gitstat.domain.model.InvalidUserException
import com.example.gitstat.domain.model.User
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val repository: Repository
) {
    suspend operator fun invoke(userName: String, forceFetchFromRemote: Boolean): Flow<Resource<User>>{
        if(userName.isBlank()){
            throw InvalidUserException("Username can't be empty...")
        }
        return repository.getUser(userName = userName, forceFetchFromRemote = forceFetchFromRemote)
    }
}