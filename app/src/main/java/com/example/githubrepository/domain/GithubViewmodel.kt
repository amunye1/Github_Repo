package com.example.githubrepository.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepository.data.data.DataResult
import com.example.githubrepository.domain.repository.GithubRepository
import com.example.githubrepository.network.SearchDao
import com.example.githubrepository.network.SearchFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GithubViewmodel @Inject constructor
    (private val repository: GithubRepository, private val searchDao: SearchDao) : ViewModel() {



    var isOffline by mutableStateOf(false)
        private set
    var searchResult by mutableStateOf<DataResult<GithubSearchResults>?>(DataResult.Idle)
        private set
    val searchDb = searchDao
    val searchList: Flow<List<SearchFields>> = searchDb.getAllSearch()

    fun addSearch(search: List<SearchFields>) {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.addSearch(search)
        }

    }

    fun deleteSearch(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.deleteSearch(id)
        }
    }

    fun updateSearch(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.updateSearch(id, name)
        }
    }
    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.clearAll()
        }
    }

    fun searchRepositories(query: String) {
        viewModelScope.launch {
            searchResult = DataResult.Loading
            searchResult =
                try {
                    val result =repository.search(query = query, 1)
                    isOffline = false
                    if(result is DataResult.Success) {
                        searchDb.clearAll()
                        searchDb.addSearch(result.data.items.map {
                            it.toSearchFields()
                        })
                    }
                    result
                } catch (e: IOException){
                    isOffline = true
                    DataResult.Error(e.message ?:"Network error")
                }catch (e: retrofit2.HttpException){
                    isOffline = false
                    DataResult.Error(e.message ?: "Http error")
                }
        }
    }
}
