package com.example.obrio_test_task.presentation.screens.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.obrio_test_task.presentation.models.PokemonUiModel
import com.example.obrio_test_task.presentation.ui.components.PokemonItem

@Composable
fun PokemonListContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    pokemonList: LazyPagingItems<PokemonUiModel>,
    listState: LazyListState,
    onPokemonClick: (Int) -> Unit,
    onFavoriteClick: (PokemonUiModel) -> Unit,
    onDeleteClick: (PokemonUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        state = listState
    ) {
        items(
            count = pokemonList.itemCount,
            key = { index ->
                pokemonList[index]?.id ?: index.hashCode()
            }
        ) { index ->
            val pokemon = pokemonList[index] ?: return@items
            PokemonItem(
                pokemon = pokemon,
                onClick = { pokemonId: Int ->
                    onPokemonClick(pokemonId)
                },
                onFavoriteClick = { pokemon: PokemonUiModel ->
                    onFavoriteClick(pokemon)
                },
                onDeleteClick = { pokemon: PokemonUiModel ->
                    onDeleteClick(pokemon)
                },
            )
        }
    }
}