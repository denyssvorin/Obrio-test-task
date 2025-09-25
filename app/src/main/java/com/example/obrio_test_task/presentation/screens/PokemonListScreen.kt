package com.example.obrio_test_task.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.obrio_test_task.R
import com.example.obrio_test_task.presentation.models.PokemonUiModel
import com.example.obrio_test_task.presentation.ui.components.PokemonItem
import com.example.obrio_test_task.presentation.viewmodels.PokemonListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val pokemonList = viewModel.pokemonData.collectAsLazyPagingItems()
    val pokemonFavoriteCount = viewModel.pokemonFavoritesCount.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToPokemon.collect { id ->
            onItemClick(id)
        }
    }

    when {
        pokemonList.loadState.refresh is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        pokemonList.loadState.refresh is LoadState.Error -> {
            val e = pokemonList.loadState.refresh as LoadState.Error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Помилка: ${e.error.localizedMessage}")
            }
        }
        pokemonList.loadState.append is LoadState.Error -> {
            val e = pokemonList.loadState.append as LoadState.Error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Помилка підвантаження: ${e.error.localizedMessage}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.pokemon_list),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        actions = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.favorite),
                                tint = Color.Red
                            )
                            Text(
                                text = pokemonFavoriteCount.value.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = modifier.padding(horizontal = 4.dp)
                            )
                        }
                    )
                },
                content = { paddingValues ->
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        items(
                            count = pokemonList.itemCount,
                            key = { index -> pokemonList[index]?.id ?: index }) { index ->
                            val pokemon = pokemonList[index] ?: return@items
                            PokemonItem(
                                pokemon = pokemon,
                                onClick = { pokemonId: Int ->
                                    viewModel.onPokemonClick(pokemonId)
                                },
                                onFavoriteClick = { pokemon: PokemonUiModel ->
                                    viewModel.onFavoriteClick(pokemon)
                                },
                                onDeleteClick = { pokemon: PokemonUiModel ->
                                    viewModel.onDeleteClick(pokemon)
                                },
                            )
                        }
                    }
                }
            )
        }
    }
}