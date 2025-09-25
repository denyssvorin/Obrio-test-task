package com.example.obrio_test_task.presentation.utils.mappers

import com.example.obrio_test_task.domain.models.PokemonModel
import com.example.obrio_test_task.presentation.models.PokemonUiModel

fun PokemonModel.toPokemonUiModel() =
    PokemonUiModel(
        id = id,
        name = name,
        image = image,
        height = height,
        weight = weight,
        isFavorite = isFavorite,
        lastUpdated = lastUpdated,
        isDeleted = isDeleted
    )

fun PokemonUiModel.toPokemonModel() =
    PokemonModel(
        id = id,
        name = name,
        image = image,
        height = height,
        weight = weight,
        isFavorite = isFavorite,
        lastUpdated = lastUpdated,
        isDeleted = isDeleted
    )