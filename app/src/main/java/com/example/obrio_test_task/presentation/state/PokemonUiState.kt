package com.example.obrio_test_task.presentation.state

import com.example.obrio_test_task.presentation.models.PokemonUiModel

data class PokemonUiState(
    val isLoading: Boolean = true,
    val pokemon: PokemonUiModel? = null,
    val error: String? = null
)
