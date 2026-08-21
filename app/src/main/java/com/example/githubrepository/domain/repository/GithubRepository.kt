package com.example.githubrepository.domain.repository

import com.example.githubrepository.data.data.DataResult
import com.example.githubrepository.domain.GithubSearchResults

interface
GithubRepository{

    suspend fun search(
        query: String,
        page: Int
    ): DataResult<GithubSearchResults>

}