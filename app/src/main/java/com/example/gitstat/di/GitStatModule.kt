package com.example.gitstat.di

import android.content.Context
import androidx.room.Room
import com.example.gitstat.data.local.Database
import com.example.gitstat.data.remote.Api
import com.example.gitstat.data.repository.RepositoryImpl
import com.example.gitstat.domain.repository.Repository
import com.example.gitstat.domain.useCases.AddUser
import com.example.gitstat.domain.useCases.GetUser
import com.example.gitstat.domain.useCases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
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
            getUser = GetUser(repository)
        )
    }
}