package com.example.githubrepository.domain

import com.example.githubrepository.network.SearchFields

fun RepoDto.toSearchFields(): SearchFields {
    return SearchFields(
        id = id.toInt(),
        name = name,
        full_name = fullName,
        private = false,
        html_url = htmlUrl,
        description = description ?: "",
        language = language ?: "",
        avart_url = owner.avatarUrl,
        stargazers_count = stargazersCount,
        forks_count = forksCount,
        fork = false,
        url = htmlUrl
    )
}