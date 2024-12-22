package com.example.gitstat.domain.model

import com.example.gitstat.data.remote.response.Author

data class Commit(
    val id: String,
    val message: String,
    val date: String
)