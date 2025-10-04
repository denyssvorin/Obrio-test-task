package com.example.obrio_test_task.presentation.screens.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.obrio_test_task.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListTopBar(modifier: Modifier = Modifier, pokemonFavoriteCount: String) {
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
                text = pokemonFavoriteCount,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(horizontal = 4.dp)
            )
        }
    )
}