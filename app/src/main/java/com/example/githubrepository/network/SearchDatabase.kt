package com.example.githubrepository.network

import androidx.room3.Database

import androidx.room3.RoomDatabase

@Database(entities = [SearchFields::class], version = 1)
 abstract class SearchDatabase : RoomDatabase() {

     companion object{
         const val NAME = "search_db"
     }

     abstract fun getSearchDao() : SearchDao
}