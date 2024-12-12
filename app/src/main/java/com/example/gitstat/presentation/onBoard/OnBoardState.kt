package com.example.gitstat.presentation.onBoard

import com.example.gitstat.domain.model.User

data class OnBoardState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String = "",
    val userName: String = "",
    val navigate: Boolean = false
)