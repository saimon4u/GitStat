package com.example.gitstat.data.remote.response

data class CommitDto(
    val commit: Committer?,
    val html_url: String?,
    val node_id: String?,
    val sha: String?,
)