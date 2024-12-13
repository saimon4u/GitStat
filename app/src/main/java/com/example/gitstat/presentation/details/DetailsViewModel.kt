package com.example.gitstat.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitstat.domain.model.InvalidUserException
import com.example.gitstat.domain.model.Repo
import com.example.gitstat.domain.useCases.UseCases
import com.example.gitstat.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val usesCases: UseCases
): ViewModel(){
    private var _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    private fun getUser(userName: String, forceFetchFromRemote: Boolean = false){
        viewModelScope.launch {
            try {
                usesCases.getUser.invoke(userName, forceFetchFromRemote).collect{response->
                    when(response){
                        is Resource.Success -> {
                            if(forceFetchFromRemote){
                                _detailsState.update {
                                    it.copy(
                                        isLoading = false,
                                        searchedUser = response.data
                                    )
                                }
                            }else {
                                _detailsState.update {
                                    it.copy(
                                        isLoading = false,
                                        user = response.data
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            _detailsState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = response.message ?: "An unexpected error occurred"
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _detailsState.update {
                                it.copy(
                                    isLoading = response.isLoading
                                )
                            }
                        }
                    }
                }
            }catch (e: InvalidUserException){
                _detailsState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }


    private fun resetErrorMessage(){
        _detailsState.update {
            it.copy(
                errorMessage = ""
            )
        }
    }

    private fun getRepos(){
        viewModelScope.launch {
            usesCases.getRepos.invoke(detailsState.value.searchedUserName).collect{response->
                when(response){
                    is Resource.Success -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                repos = response.data
                            )
                        }
                    }
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = response.isLoading
                            )
                        }
                    }
                }
            }
        }
    }



    private fun getLanguages(repoName: String){
        viewModelScope.launch {
            usesCases.getLanguages.invoke(detailsState.value.searchedUserName, repoName).collect{response->
                when(response){
                    is Resource.Success -> {
                        _detailsState.update {
                            val updatedLanguages = it.usedLanguages.toMutableMap()
                            response.data?.forEach { (language, count) ->
                                updatedLanguages[language] = updatedLanguages.getOrDefault(language, 0) + count
                            }
                            it.copy(
                                isLoading = false,
                                usedLanguages = updatedLanguages
                            )
                        }
                    }
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = response.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getDeployments(repoName: String){
        viewModelScope.launch {
            usesCases.getDeployments.invoke(detailsState.value.searchedUserName, repoName).collect{response->
                when(response){
                    is Resource.Success -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                deploymentCount = it.deploymentCount + (response.data ?: 0)
                            )
                        }
                    }
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = response.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCommits(repoName: String){
        viewModelScope.launch {
            usesCases.getCommits.invoke(detailsState.value.searchedUserName, repoName).collect{response->
                when(response){
                    is Resource.Success -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                commitCount = it.commitCount + (response.data ?: 0)
                            )
                        }
                    }
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message ?: "An unexpected error occurred"
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(
                                isLoading = response.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.EnteredUserName -> {
                _detailsState.update {
                    it.copy(
                        searchedUserName = event.userName
                    )
                }
            }

            is DetailsEvent.ResetErrorMessage -> {
                resetErrorMessage()
            }

            is DetailsEvent.GetUser -> {
                getUser(event.userName)
            }

            is DetailsEvent.Search -> {
                getUser(_detailsState.value.searchedUserName, true)
                if(detailsState.value.errorMessage.isEmpty()){
                    getRepos()
                }
                if(detailsState.value.errorMessage.isEmpty()){
                    detailsState.value.repos?.forEach{ repo->
                        getLanguages(repo.name)
                        getDeployments(repo.name)
                        getCommits(repo.name)
                    }
                }
            }
        }
    }
}