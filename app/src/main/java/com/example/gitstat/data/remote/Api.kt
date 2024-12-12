package com.example.gitstat.data.remote

import com.example.gitstat.data.remote.response.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{userName}")
    suspend fun getUser(
        @Path("userName") type: String,
    ): UserDto

    companion object{
        const val BASE_URL = "https://api.github.com/"
    }
}