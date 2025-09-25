package com.example.obrio_test_task.domain.models

data class PokemonModel(
    val id: Int,
    val name: String,
    val image: String,
    val height: Int?,
    val weight: Int?,
    val isFavorite: Boolean,
    val lastUpdated: Long,
    val isDeleted: Boolean
)
