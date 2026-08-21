package com.example.githubrepository.data.data

import com.example.githubrepository.domain.GithubSearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
   @GET("search/repositories")
    suspend fun search
               (@Query("q")q:String,
                @Query("sort")sort:String?,
                @Query("order")order:String?,
                @Query("per_page")perPage:Int,
                @Query("page")page:Int
    ): Response<GithubSearchResults>


}