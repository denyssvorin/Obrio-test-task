package com.example.obrio_test_task.presentation.models

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val image: String,
    val height: Int?,
    val weight: Int?,
    val isFavorite: Boolean,
    val lastUpdated: Long,
    val isDeleted: Boolean
)
