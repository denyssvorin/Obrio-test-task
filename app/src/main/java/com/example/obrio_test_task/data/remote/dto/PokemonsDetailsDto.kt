package com.example.obrio_test_task.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailsDto(
    val id: Int,
    val name: String,
    val sprites: PokemonImageUrl,
    val height: Int,
    val weight: Int
)

data class PokemonImageUrl(
    @SerializedName("front_default") val imageUrl: String
)
