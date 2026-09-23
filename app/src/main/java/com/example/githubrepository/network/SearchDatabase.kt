package com.example.githubrepository.network

import androidx.room3.Database

import androidx.room3.RoomDatabase

@Database(entities = [SearchFields::class], version = 2)
 abstract class SearchDatabase : RoomDatabase() {

     companion object{
         const val NAME = "search_db"
     }

     abstract fun getSearchDao() : SearchDao
}

@Database(entities = [SelectedFields::class], version = 2)
 abstract class SelectedDatabase : RoomDatabase() {
     companion object{
         const val NAME = "selected_db"
     }

    abstract fun getSelectedDao() : SelectedDao
 }
