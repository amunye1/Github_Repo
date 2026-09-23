package com.example.githubrepository.di

import android.content.Context
import androidx.room3.Room
import com.example.githubrepository.network.SearchDao
import com.example.githubrepository.network.SearchDatabase
import com.example.githubrepository.network.SelectedDao
import com.example.githubrepository.network.SelectedDatabase
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
     fun provideSearchMyDatabase(@ApplicationContext context: Context): SearchDatabase {
        return Room.databaseBuilder(
            context,
            SearchDatabase::class.java,
            SearchDatabase.NAME
        ).build()


    }
    @Provides
    @Singleton
    fun provideSearchMyDao(database: SearchDatabase): SearchDao {
        return database.getSearchDao()
    }


    @Provides
    @Singleton
    fun provideSelectedMyDatabase(@ApplicationContext context: Context): SelectedDatabase {
        return Room.databaseBuilder(
            context,
            SelectedDatabase::class.java,
            SelectedDatabase.NAME
        ).build()


    }
    @Provides
    @Singleton
    fun provideSelectedMyDao(database: SelectedDatabase): SelectedDao {
        return database.getSelectedDao()
    }

}
