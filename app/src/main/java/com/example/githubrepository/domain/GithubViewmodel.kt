package com.example.githubrepository.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepository.data.data.DataResult
import com.example.githubrepository.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GithubViewmodel @Inject constructor
    (private val repository: GithubRepository) : ViewModel() {

    var searchResult by mutableStateOf<DataResult<GithubSearchResults>?>(DataResult.Idle)
        private set

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            searchResult = DataResult.Loading
            searchResult =
                try {
                    repository.search(query = query, 1)
                } catch (e: IOException){
                    DataResult.Error(e.message ?:"Network error")
                }catch (e: retrofit2.HttpException){
                    DataResult.Error(e.message ?: "Http error")
                }
        }
    }
}
