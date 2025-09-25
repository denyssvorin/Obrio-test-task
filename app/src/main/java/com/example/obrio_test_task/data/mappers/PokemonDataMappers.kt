package com.example.obrio_test_task.data.mappers

import com.example.obrio_test_task.data.local.entities.PokemonDetailsEntity
import com.example.obrio_test_task.data.remote.dto.PokemonDetailsDto
import com.example.obrio_test_task.data.remote.dto.PokemonDto
import com.example.obrio_test_task.domain.models.PokemonModel

fun PokemonDetailsDto.toPokemonEntity(isFavorite: Boolean, lastUpdated: Long) =
    PokemonDetailsEntity(
        id = id,
        name = name,
        image = sprites.imageUrl,
        height = height,
        weight = weight,
        isFavorite = isFavorite,
        lastUpdated = lastUpdated
    )

fun PokemonModel.toPokemonEntity() =
    PokemonDetailsEntity(
        id = id,
        name = name,
        image = image,
        height = height,
        weight = weight,
        isFavorite = isFavorite,
        lastUpdated = lastUpdated,
        isDeleted = isDeleted
    )

fun PokemonDetailsEntity.toPokemonModel() =
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


fun PokemonDto.toPokemonEntity(lastUpdated: Long): PokemonDetailsEntity {

    val id = this.url.toId()
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"

    return PokemonDetailsEntity(
        id = id,
        name = name,
        image = imageUrl,
        lastUpdated = lastUpdated
    )
}


fun String.toId(): Int =
    this.trimEnd('/').split("/").last().toInt()