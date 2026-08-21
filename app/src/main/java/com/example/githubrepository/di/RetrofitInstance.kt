package com.example.githubrepository.di

import android.app.Application
import com.example.githubrepository.data.data.GithubApi
import com.example.githubrepository.data.repository.GithubRepositoryImpl
import com.example.githubrepository.domain.repository.GithubRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {
    private const val BASE_URL ="https://api.github.com/"
    @Provides
    @Singleton
    fun provideMyApi(): GithubApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: GithubApi, app: Application): GithubRepository {
    return GithubRepositoryImpl(api, app)}


}