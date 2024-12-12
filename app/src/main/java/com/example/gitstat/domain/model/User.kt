package com.example.gitstat.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: Int,
    val avatarUrl: String,
    val bio: String,
    val createdAt: String,
    val eventsUrl: String,
    val followersCount: Int,
    val followingCount: Int,
    val htmlUrl: String,
    val login: String,
    val publicGistsCount: Int,
    val publicReposCount: Int,
    val reposUrl: String,
    val type: String,
)

class InvalidUserException(message: String): Exception(message)
