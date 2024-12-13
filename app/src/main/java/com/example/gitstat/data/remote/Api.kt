package com.example.gitstat.data.remote

import com.example.gitstat.data.remote.response.CommitDto
import com.example.gitstat.data.remote.response.DeploymentDto
import com.example.gitstat.data.remote.response.RepoDto
import com.example.gitstat.data.remote.response.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{userName}")
    suspend fun getUser(
        @Path("userName") type: String,
    ): UserDto

    @GET("users/{userName}/repos")
    suspend fun getRepos(
        @Path("userName") type: String,
    ): List<RepoDto>

    @GET("repos/{userName}/{repoName}/languages")
    suspend fun getLanguages(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Map<String, Int>

    @GET("/repos/{userName}/{repoName}/deployments")
    suspend fun getDeployments(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): List<DeploymentDto>

    @GET("/repos/{userName}/{repoName}/commits")
    suspend fun getCommits(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): List<CommitDto>

    companion object{
        const val BASE_URL = "https://api.github.com/"
    }
}