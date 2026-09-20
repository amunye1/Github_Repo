package com.example.githubrepository.di

import android.content.Context
import androidx.room3.Room
import com.example.githubrepository.network.SearchDao
import com.example.githubrepository.network.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Database {


    @Provides
    @Singleton
     fun provideMyDatabase(@ApplicationContext context: Context): SearchDatabase {
        return Room.databaseBuilder(
            context,
            SearchDatabase::class.java,
            SearchDatabase.NAME
        ).build()


    }
    @Provides
    @Singleton
    fun provideMyDao(database: SearchDatabase): SearchDao {
        return database.getSearchDao()
    }
}
