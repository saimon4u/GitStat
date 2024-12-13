package com.example.gitstat.domain.useCases

import com.example.gitstat.domain.model.InvalidUserException
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetCommits(
    private val repository: Repository
) {
    suspend operator fun invoke(userName: String, repoName: String): Flow<Resource<Int>> {
        if(userName.isBlank()){
            throw InvalidUserException("Username can't be empty...")
        }
        if(repoName.isBlank()){
            throw InvalidUserException("Repository name can't be empty...")
        }
        return repository.getCommits(userName, repoName)
    }
}