package com.example.githubrepository.data.repository

import android.app.Application
import com.example.githubrepository.data.data.DataResult
import com.example.githubrepository.data.data.GithubApi
import com.example.githubrepository.domain.GithubSearchResults
import com.example.githubrepository.domain.repository.GithubRepository

class GithubRepositoryImpl(private val api: GithubApi, appContext: Application) : GithubRepository {

    override suspend fun search(
        query: String,
        page: Int
    ): DataResult<GithubSearchResults> {
        val response = api.search(q = query, sort = null, order = null, perPage = 30, page = page)

        if (response.isSuccessful) {
            val body = response.body()
            return if (body != null) {
                DataResult.Success(body)
            } else {
                DataResult.Error("Response body is null")
            }
        } else {
            return DataResult.Error("Request failed with code ${response.code()}")
        }

    }

}


