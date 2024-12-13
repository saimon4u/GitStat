package com.example.gitstat.domain.model

data class Repo(
    val name: String,
    val isPrivate: Boolean,
    val repoLink: String,
    val forksCount: Int,
    val eventsUrl: String,
    val languagesUrl: String,
    val commitsUrl: String,
    val deploymentsUrl: String,
    val size: Int,
    val watchersCount: Int,
)