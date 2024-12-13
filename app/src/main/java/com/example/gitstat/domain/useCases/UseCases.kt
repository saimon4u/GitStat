package com.example.gitstat.domain.useCases


data class UseCases(
    val addUser: AddUser,
    val getUser: GetUser,
    val getRepos: GetRepos,
    val getLanguages: GetLanguages,
    val getDeployments: GetDeployments,
    val getCommits: GetCommits
)