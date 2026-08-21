package com.example.githubrepository.domain

import com.google.gson.annotations.SerializedName


data class  GithubSearchResults (
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<RepoDto>,
)

data class RepoDto(
    val id: Long,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val description: String?,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    val language: String?,
    val owner: OwnerDto,
)

data class OwnerDto(
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)