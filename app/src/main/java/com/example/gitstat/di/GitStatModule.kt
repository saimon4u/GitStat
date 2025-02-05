package com.example.gitstat.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.room.Room
import com.example.gitstat.data.local.Database
import com.example.gitstat.data.remote.Api
import com.example.gitstat.data.repository.RepositoryImpl
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.domain.useCases.AddUser
import com.example.gitstat.domain.useCases.GetCommits
import com.example.gitstat.domain.useCases.GetDeployments
import com.example.gitstat.domain.useCases.GetLanguages
import com.example.gitstat.domain.useCases.GetRepos
import com.example.gitstat.domain.useCases.GetUser
import com.example.gitstat.domain.useCases.UseCases
import com.example.gitstat.presentation.utils.PERSONAL_ACCESS_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GitStatModule{

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $PERSONAL_ACCESS_TOKEN")
            .build()
        chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesGithubApi(): Api {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Api.BASE_URL)
            .client(client)
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "Database.db"
        ).build()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Provides
    @Singleton
    fun providesRepository(database: Database, api: Api): Repository{
        return RepositoryImpl(
            api = api,
            database =database
        )
    }

    @Provides
    @Singleton
    fun providesUseCases(
        repository: Repository
    ): UseCases{
        return UseCases(
            addUser = AddUser(repository),
            getUser = GetUser(repository),
            getRepos = GetRepos(repository),
            getLanguages = GetLanguages(repository),
            getDeployments = GetDeployments(repository),
            getCommits = GetCommits(repository)
        )
    }
}