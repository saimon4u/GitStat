package com.example.gitstat.presentation.onBoard

sealed class OnBoardEvent {
    data class EnteredUserName(val userName: String): OnBoardEvent()
    data object GetStarted: OnBoardEvent()
    data object ResetErrorMessage: OnBoardEvent()
}