package com.example.gitstat.domain.useCases

import com.example.gitstat.domain.model.InvalidUserException
import com.example.gitstat.domain.model.User
import com.example.gitstat.domain.repository.Repository

class AddUser(
    private val repository: Repository
) {
    suspend operator fun invoke(user: User){
        if(user.login.isBlank()){
            throw InvalidUserException("Username can't be empty...")
        }
        repository.insertUser(user)
    }
}