package com.example.githubrepository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.githubrepository.data.data.DataResult
import com.example.githubrepository.domain.GithubViewmodel
import com.example.githubrepository.ui.theme.GithubRepositoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubRepositoryTheme {
                //scope it to the navigation in this case im making the dagger hilt scoped to
                //view model
              val viewModel = hiltViewModel<GithubViewmodel>()
                val searchText = remember{mutableStateOf("")}




                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->

                    Column(
                       modifier = Modifier.padding(padding)
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 52.dp)
                        ) {
                            TextField(

                                value=searchText.value,
                                onValueChange={searchText.value=it},
                                label= {Text("Search")},
                            )
                            Button(

                                onClick = {
                                    if (searchText.value.isNotEmpty()) {
                                        viewModel.searchRepositories(searchText.value)
                                    }

                                },


                            ){
                                Text("Search")
                            }

                        }

                        when (val result = viewModel.searchResult) {
                            null -> Text("Type something and press Search")
                            is DataResult.Loading -> {
                                Text("Loading...", modifier = Modifier.padding(padding))
                            }
                            is DataResult.Success -> {
                                LazyColumn() {
                                    items(result.data.items) { repo ->
                                        Text(repo.fullName)
                                        repo.description?.let { Text(it) }
                                        Text(repo.owner.avatarUrl)
                                        Text(repo.stargazersCount.toString())
                                        Text(repo.language.toString())
                                    }
                                }
                            }
                            is DataResult.Error -> {
                                Text("Error: ${result.message}", modifier = Modifier.padding(padding))
                            }
                        }

                    }


                }
            }
        }
    }
}

