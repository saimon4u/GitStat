package com.example.gitstat.presentation.details

import com.example.gitstat.domain.model.Commit
import com.example.gitstat.domain.model.Repo
import com.example.gitstat.domain.model.User

data class DetailsState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val searchedUserName: String = "",
    val searchedUser: User? = null,
    val repos: List<Repo>? = null,
    val usedLanguages: Map<String, Int> = emptyMap(),
    val deploymentCount: Int = 0,
    val commitList: List<Commit> = emptyList(),
    val isFetchingComplete: Boolean = false
)