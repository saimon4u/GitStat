package com.example.gitstat.presentation.onBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitstat.domain.model.InvalidUserException
import com.example.gitstat.domain.model.User
import com.example.gitstat.domain.useCases.UseCases
import com.example.gitstat.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val usesCases: UseCases
): ViewModel(){
    private var _onBoardState = MutableStateFlow(OnBoardState())
    val onBoardState = _onBoardState.asStateFlow()

    private fun getUser(userName: String){
        viewModelScope.launch {
            try {
                usesCases.getUser.invoke(userName, true).collect{response->
                    when(response){
                        is Resource.Success -> {
                            _onBoardState.update {
                                it.copy(
                                    isLoading = false,
                                    user = response.data
                                )
                            }
                            insertUser(onBoardState.value.user!!)
                        }
                        is Resource.Error -> {
                            _onBoardState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = response.message ?: "An unexpected error occurred"
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _onBoardState.update {
                                it.copy(
                                    isLoading = response.isLoading
                                )
                            }
                        }
                    }
                }
            }catch (e: InvalidUserException){
                _onBoardState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }


    private fun resetErrorMessage(){
        _onBoardState.update {
            it.copy(
                errorMessage = ""
            )
        }
    }

    private fun insertUser(user: User){
        viewModelScope.launch {
            try{
                usesCases.addUser.invoke(user)
                _onBoardState.update {
                    it.copy(
                        navigate = true
                    )
                }
            }catch (e: InvalidUserException){
                _onBoardState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    fun onEvent(event: OnBoardEvent){
        when(event){
            is OnBoardEvent.EnteredUserName -> {
                _onBoardState.update {
                    it.copy(
                        userName = event.userName
                    )
                }
            }
            is OnBoardEvent.GetStarted -> {
                getUser(onBoardState.value.userName)
            }
            is OnBoardEvent.ResetErrorMessage -> {
                resetErrorMessage()
            }
        }
    }
}