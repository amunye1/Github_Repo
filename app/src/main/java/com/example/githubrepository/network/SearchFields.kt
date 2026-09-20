package com.example.githubrepository.network

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class SearchFields(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var full_name: String,
    var private: Boolean,
    var html_url: String,
    var description: String,
    var language: String,
    var avart_url: String,
    var stargazers_count: Int,
    var forks_count: Int,
    var fork: Boolean,
    var url: String,

)
