package com.example.obrio_test_task.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenDestinations {

    @Serializable
    object PokemonList: ScreenDestinations()

    @Serializable
    data class PokemonDetails(val id: Int): ScreenDestinations()
}