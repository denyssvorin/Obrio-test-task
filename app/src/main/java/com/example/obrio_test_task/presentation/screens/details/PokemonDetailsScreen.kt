package com.example.obrio_test_task.presentation.screens.details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.obrio_test_task.presentation.screens.components.Error
import com.example.obrio_test_task.presentation.screens.components.Loading
import com.example.obrio_test_task.presentation.screens.details.components.PokemonDetailsContent
import com.example.obrio_test_task.presentation.screens.details.components.PokemonDetailsTopBar
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
            Loading()
        }

        uiState.error != null -> {
            Error(errorMessage = uiState.error)
        }

        uiState.pokemon != null -> {
            Scaffold(
                topBar = {
                    PokemonDetailsTopBar(
                        isFavorite = uiState.pokemon.isFavorite,
                        onBackClick = onBackClick,
                        onFavoriteClick = { viewModel.onFavoriteClick(uiState.pokemon) }
                    )
                },
                content = { paddingValues ->
                    PokemonDetailsContent(paddingValues = paddingValues, pokemon = uiState.pokemon)
                }
            )
        }
    }
}