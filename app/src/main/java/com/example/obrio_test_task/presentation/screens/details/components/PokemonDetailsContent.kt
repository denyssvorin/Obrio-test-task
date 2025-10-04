package com.example.obrio_test_task.presentation.screens.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.obrio_test_task.R
import com.example.obrio_test_task.presentation.models.PokemonUiModel
import com.example.obrio_test_task.presentation.ui.components.CachedImage

@Composable
fun PokemonDetailsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    pokemon: PokemonUiModel,
) {
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
                url = pokemon.image,
                modifier = Modifier.size(240.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.name, pokemon.name),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(R.string.id, pokemon.id),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(
                    R.string.height,
                    pokemon.height.toString()
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(
                    R.string.weight,
                    pokemon.weight.toString()
                ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}