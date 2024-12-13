package com.example.gitstat.domain.useCases

import com.example.gitstat.domain.model.InvalidUserException
import com.example.gitstat.domain.model.Repo
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetRepos(
    private val repository: Repository
) {
    suspend operator fun invoke(userName: String): Flow<Resource<List<Repo>>> {
        if(userName.isBlank()){
            throw InvalidUserException("Username can't be empty...")
        }
        return repository.getRepos(userName = userName)
    }
}