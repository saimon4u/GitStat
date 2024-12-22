package com.example.gitstat.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.gitstat.data.local.Database
import com.example.gitstat.data.remote.Api
import com.example.gitstat.data.toCommit
import com.example.gitstat.data.toRepo
import com.example.gitstat.data.toUser
import com.example.gitstat.domain.model.Commit
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
                emit(Resource.Error("Error occurred while fetching user..."))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching user..."))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching user..."))
                return@flow
            }

            val userEntity = userFromApi.toUser()

            emit(Resource.Success(userEntity))

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
                emit(Resource.Error("Error occurred while fetching repos..."))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching repos..."))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching repos..."))
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
                emit(Resource.Error("Error occurred while fetching languages..."))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching languages..."))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching languages..."))
                return@flow
            }

            emit(Resource.Success(languagesFromApi))


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
                emit(Resource.Error("Error occurred while fetching deployments..."))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching deployments..."))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching deployments..."))
                return@flow
            }

            emit(Resource.Success(deploymentsFromApi.size))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCommits(userName: String, repoName: String): Flow<Resource<List<Commit>>> {
        return flow {
            emit(Resource.Loading(true))

            val commitsFromApi = try{
                api.getCommits(userName, repoName)
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching commits..."))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching commits..."))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error("Error occurred while fetching commits..."))
                return@flow
            }

            val commitList = commitsFromApi.map {
                it.toCommit()
            }

            emit(Resource.Success(commitList))

            emit(Resource.Loading(false))
        }
    }

}