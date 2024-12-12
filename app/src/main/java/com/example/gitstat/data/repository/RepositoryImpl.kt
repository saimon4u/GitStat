package com.example.gitstat.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.gitstat.data.local.Database
import com.example.gitstat.data.remote.Api
import com.example.gitstat.data.toUser
import com.example.gitstat.domain.model.User
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val database: Database
): Repository{
    override suspend fun insertUser(user: User) {
        database.dao.insertUser(user)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getUser(
        forceFetchFromRemote: Boolean,
        userName: String
    ): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))

            val localUser = database.dao.getUser(userName)

            if(!forceFetchFromRemote){
                emit(Resource.Success(
                    data = localUser
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            val userFromApi = try{
                api.getUser(userName)
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Something went wrong..."))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Something went wrong..."))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error("Something went wrong..."))
                return@flow
            }

            val userEntity = userFromApi.toUser()

            database.dao.insertUser(userEntity)

            emit(Resource.Success(
                userEntity
            ))

            emit(Resource.Loading(false))
        }
    }

}