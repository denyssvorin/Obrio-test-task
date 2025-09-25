package com.example.obrio_test_task.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.obrio_test_task.R
import com.example.obrio_test_task.presentation.models.PokemonUiModel

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: PokemonUiModel,
    onFavoriteClick: (PokemonUiModel) -> Unit,
    onDeleteClick: (PokemonUiModel) -> Unit,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(pokemon.id) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CachedImage(
            url = pokemon.image,
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "ID: ${pokemon.id}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }

        IconButton(onClick = { onFavoriteClick(pokemon) }) {
            Icon(
                imageVector = if (pokemon.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite),
                tint = if (pokemon.isFavorite) Color.Red else Color.Gray
            )
        }

        IconButton(onClick = { onDeleteClick(pokemon) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Gray
            )
        }
    }
}
