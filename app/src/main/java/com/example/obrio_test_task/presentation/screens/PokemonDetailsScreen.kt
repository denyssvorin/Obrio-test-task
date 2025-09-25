package com.example.obrio_test_task.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.obrio_test_task.R
import com.example.obrio_test_task.presentation.ui.components.CachedImage
import com.example.obrio_test_task.presentation.viewmodels.PokemonDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    pokemonId: Int,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(pokemonId) {
        viewModel.setPokemonId(pokemonId)
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Помилка: ${uiState.error}")
            }
        }

        uiState.pokemon != null -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.pokemon_details),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.onFavoriteClick(uiState.pokemon)
                            }) {
                                Icon(
                                    imageVector = if (uiState.pokemon.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = stringResource(R.string.favorite),
                                    tint = if (uiState.pokemon.isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CachedImage(
                                url = uiState.pokemon.image,
                                modifier = Modifier.size(240.dp)
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = "Name: ${uiState.pokemon.name}",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                text = "ID: ${uiState.pokemon.id}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Height: ${uiState.pokemon.height}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Weight: ${uiState.pokemon.weight}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            )
        }
    }
}