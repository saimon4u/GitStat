package com.example.gitstat.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.gitstat.domain.model.User

@Dao
interface Dao {
    @Upsert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE login = :userName")
    suspend fun getUser(userName: String): User
}