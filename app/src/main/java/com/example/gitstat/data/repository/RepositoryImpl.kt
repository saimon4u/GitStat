package com.example.gitstat.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.gitstat.data.local.Database
import com.example.gitstat.data.remote.Api
import com.example.gitstat.data.toRepo
import com.example.gitstat.data.toUser
import com.example.gitstat.domain.model.Repo
import com.example.gitstat.domain.model.User
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.presentation.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val database: Database
): Repository{
    override suspend fun insertUser(user: User) {
        database.dao.insertUser(user)
    }

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

    override suspend fun getRepos(userName: String): Flow<Resource<List<Repo>>> {
        return flow {
            emit(Resource.Loading(true))

            val reposFromApi = try{
                api.getRepos(userName)
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

            val repos = reposFromApi.map {
                it.toRepo()
            }

            emit(Resource.Success(repos))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getLanguages(
        userName: String,
        repoName: String
    ): Flow<Resource<Map<String, Int>>> {
        return flow {
            emit(Resource.Loading(true))

            val languagesFromApi = try{
                api.getLanguages(userName, repoName)
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

            emit(Resource.Success(languagesFromApi))

            println(languagesFromApi)

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getDeployments(userName: String, repoName: String): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading(true))

            val deploymentsFromApi = try{
                api.getDeployments(userName, repoName)
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

            emit(Resource.Success(deploymentsFromApi.size))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCommits(userName: String, repoName: String): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading(true))

            val commitsFromApi = try{
                api.getCommits(userName, repoName)
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

            emit(Resource.Success(commitsFromApi.size))

            emit(Resource.Loading(false))
        }
    }

}