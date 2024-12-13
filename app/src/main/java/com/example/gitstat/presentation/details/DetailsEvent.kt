package com.example.gitstat.presentation.details

sealed class DetailsEvent {
    data class EnteredUserName(val userName: String): DetailsEvent()
    data object ResetErrorMessage: DetailsEvent()
    data class GetUser(val userName: String): DetailsEvent()
    data object Search: DetailsEvent()
}