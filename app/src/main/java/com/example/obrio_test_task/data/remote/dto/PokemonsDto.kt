package com.example.obrio_test_task.data.remote.dto


data class PokemonResultsDto(
    val results: List<PokemonDto>
)

data class PokemonDto(
    val name: String,
    val url: String
)