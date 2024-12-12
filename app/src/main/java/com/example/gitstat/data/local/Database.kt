package com.example.gitstat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitstat.domain.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val dao: Dao
}