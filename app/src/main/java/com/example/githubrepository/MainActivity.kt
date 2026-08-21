package com.example.githubrepository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

                LaunchedEffect(Unit) {
                    viewModel.searchRepositories()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    when (val result = viewModel.searchResult) {
                        is DataResult.Loading -> {
                            Text("Loading...", modifier = Modifier.padding(padding))
                        }
                        is DataResult.Success -> {
                            LazyColumn(modifier = Modifier.padding(padding)) {
                                items(result.data.items) { repo ->
                                    Text(repo.fullName)
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

