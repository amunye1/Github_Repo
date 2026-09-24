package com.example.githubrepository

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubrepository.data.data.DataResult
import com.example.githubrepository.domain.GithubViewmodel
import com.example.githubrepository.ui.theme.GithubRepositoryTheme
import dagger.hilt.android.AndroidEntryPoint
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalGlideComposeApi::class)
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
                            null ,DataResult.Idle -> Text("Type something and press Search")
                            is DataResult.Loading -> {
                                Text("Loading...", modifier = Modifier.padding(padding))
                            }
                            is DataResult.Success -> {
                                if(viewModel.isOffline){
                                    Text("Offline - showing last saved results", modifier =
                                        Modifier.padding(padding))
                                }
                                LazyColumn() {

                                    items(result.data.items, key= {it.id }) { repo ->



                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(
                                                        color = Color.LightGray,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .padding(16.dp)
                                                    .border(
                                                        2.dp,
                                                        Color.Black,)

                                            ) {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {

                                                    Row(modifier = Modifier.fillMaxWidth()) {
                                                        GlideImage(
                                                            model = repo.owner.avatarUrl,
                                                            contentDescription = "Loaded image from URL",
                                                            modifier = Modifier
                                                                .size(64.dp)
                                                                .clip(CircleShape)                       // clip to the circle shape
                                                                .border(
                                                                    2.dp,
                                                                    Color.Gray,
                                                                    CircleShape
                                                                )   // add a border (optional)

                                                        )
                                                        Text(
                                                            repo.fullName + "name",
                                                            modifier = Modifier.fillMaxWidth(),
                                                            color = Color.Blue,                          // Text color
                                                            fontSize = 24.sp,                            // Size (always use .sp)
                                                            fontWeight = FontWeight.Bold,                // Bold, Light, Thin, etc.
                                                            fontStyle = FontStyle.Italic,                // Italic or Normal
                                                            letterSpacing = 2.sp,                        // Distance between characters
                                                            textAlign = TextAlign.Center,                // Alignment
                                                            textDecoration = TextDecoration.Underline,   // Underline or LineThrough
                                                            style = MaterialTheme.typography.bodyLarge   // Base typography theme

                                                        )

                                                    }



                                                    repo.description?.let {
                                                        Text(
                                                            it,
                                                            modifier = Modifier.fillMaxWidth(),
                                                            color = Color.Blue,                          // Text color
                                                            fontSize = 24.sp,                            // Size (always use .sp)
                                                            fontWeight = FontWeight.Bold,                // Bold, Light, Thin, etc.
                                                            fontStyle = FontStyle.Italic,                // Italic or Normal
                                                            letterSpacing = 2.sp,                        // Distance between characters
                                                            textAlign = TextAlign.Center,                // Alignment
                                                            textDecoration = TextDecoration.Underline,   // Underline or LineThrough
                                                            style = MaterialTheme.typography.bodyLarge
                                                        )
                                                    }

                                                    Text(
                                                        repo.stargazersCount.toString(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                        color = Color.Blue,                          // Text color
                                                        fontSize = 24.sp,                            // Size (always use .sp)
                                                        fontWeight = FontWeight.Bold,                // Bold, Light, Thin, etc.
                                                        fontStyle = FontStyle.Italic,                // Italic or Normal
                                                        letterSpacing = 2.sp,                        // Distance between characters
                                                        textAlign = TextAlign.Center,                // Alignment
                                                        textDecoration = TextDecoration.Underline,   // Underline or LineThrough
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )

                                                    Text(
                                                        repo.language.toString(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                        color = Color.Blue,                          // Text color
                                                        fontSize = 24.sp,                            // Size (always use .sp)
                                                        fontWeight = FontWeight.Bold,                // Bold, Light, Thin, etc.
                                                        fontStyle = FontStyle.Italic,                // Italic or Normal
                                                        letterSpacing = 2.sp,                        // Distance between characters
                                                        textAlign = TextAlign.Center,                // Alignment
                                                        textDecoration = TextDecoration.Underline,   // Underline or LineThrough
                                                        style = MaterialTheme.typography.bodyLarge

                                                    )


                                                }
                                            }


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

