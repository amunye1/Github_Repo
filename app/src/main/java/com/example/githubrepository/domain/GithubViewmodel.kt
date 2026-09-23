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
import com.example.githubrepository.network.SelectedDao
import com.example.githubrepository.network.SelectedFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GithubViewmodel @Inject constructor
    (private val repository: GithubRepository, private val searchDao: SearchDao, private  val selectedDao: SelectedDao) : ViewModel() {



    var isOffline by mutableStateOf(false)
        private set

    var isSelectedFieldset by mutableStateOf(false)
        private set

    var isSearchFieldset by mutableStateOf(false)
    var searchResult by mutableStateOf<DataResult<GithubSearchResults>?>(DataResult.Idle)
        private set
    val searchDb = searchDao
    val selectedDb = selectedDao
    val searchList: Flow<List<SearchFields>> = searchDb.getAllSearch()
    val selectedList: Flow<List<SearchFields>> = selectedDb.getAllSearch()

    fun addSearch(search: List<SearchFields>) {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.addSearch(search)
        }

    }

    fun addSelected(search: List<SelectedFields>) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedDb.addSearch(search)
        }
    }

    fun deleteSearch(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.deleteSearch(id)
        }
    }

    fun deleteSelected(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedDb.deleteSearch(id)
        }
    }

    fun updateSearch(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.updateSearch(id, name)
        }
    }

    fun updateSelected(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedDb.updateSearch(id, name)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            searchDb.clearAll()
        }
    }

    fun clearAllSelected(){
        viewModelScope.launch(Dispatchers.IO) {
            selectedDb.clearAll()
        }
    }

    fun clearSelected(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            selectedDb.clear(id)
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
                        if(isSelectedFieldset){

                            selectedDb.addSearch(result.data.items.map {
                                it.toSelectedFields()
                            })
                        }else if(isSearchFieldset){
                        searchDb.clearAll()
                        searchDb.addSearch(result.data.items.map {
                            it.toSearchFields()
                        })
                            }
                    }
                    result
                } catch (e: IOException){
                    isOffline = true
                    if(isOffline && isSearchFieldset) {
                        val cached = searchDb.getAllSearchOnce()
                        if (cached.isNotEmpty()) {
                            DataResult.Success(
                                GithubSearchResults(
                                    totalCount = cached.size,
                                    incompleteResults = false,
                                    items = cached.map { it.toRepoDto() }
                                )
                            )

                        } else {
                            print("ERROR IN IS SEARCH FIELDSET AND IS OFFLINE")
                            DataResult.Error(e.message ?: "Network error")
                        }
                    }else if(isOffline && isSelectedFieldset){
                        val cached = selectedDb.getAllSearchOnce()
                        if (cached.isNotEmpty()) {
                            DataResult.Success(
                                GithubSearchResults(
                                    totalCount = cached.size,
                                    incompleteResults = false,
                                    items = cached.map { it.toRepoDto() }
                                )
                            )

                        } else {
                            print("ERROR IN IS SELECTED FIELDSET AND IS OFFLINE")
                            DataResult.Error(e.message ?: "Network error")
                        }
                    }
                    else {
                        DataResult.Error(e.message ?: "Network error")
                    }
                }catch (e: retrofit2.HttpException){
                    isOffline = false
                    DataResult.Error(e.message ?: "Http error")
                }
        }
    }
}
