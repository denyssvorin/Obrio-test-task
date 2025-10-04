package com.example.obrio_test_task.presentation.screens.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.obrio_test_task.R
import com.example.obrio_test_task.presentation.models.PokemonUiModel
import com.example.obrio_test_task.presentation.screens.components.Error
import com.example.obrio_test_task.presentation.screens.components.Loading
import com.example.obrio_test_task.presentation.screens.list.components.PokemonListContent
import com.example.obrio_test_task.presentation.screens.list.components.PokemonListTopBar
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
    val listState = viewModel.listState

    LaunchedEffect(Unit) {
        viewModel.navigateToPokemon.collect { id ->
            onItemClick(id)
        }
    }

    when {
        pokemonList.loadState.refresh is LoadState.Loading -> {
            Loading()
        }

        pokemonList.loadState.refresh is LoadState.Error -> {
            val error = (pokemonList.loadState.refresh as LoadState.Error)
                .error
                .localizedMessage
                ?: stringResource(
                    R.string.unknown_error
                )
            Error(errorMessage = error)
        }

        pokemonList.loadState.append is LoadState.Error -> {
            val error = (pokemonList.loadState.append as LoadState.Error)
                .error
                .localizedMessage
                ?: stringResource(
                    R.string.unknown_error
                )
            Error(errorMessage = error)
        }

        else -> {
            Scaffold(
                topBar = {
                    PokemonListTopBar(pokemonFavoriteCount = pokemonFavoriteCount.value.toString())
                },
                content = { paddingValues ->
                    PokemonListContent(
                        paddingValues = paddingValues,
                        pokemonList = pokemonList,
                        listState = listState,
                        onPokemonClick = { pokemonId: Int ->
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
            )
        }
    }
}