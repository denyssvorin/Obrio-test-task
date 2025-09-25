package com.example.obrio_test_task.data.remote.api

import com.example.obrio_test_task.data.remote.dto.PokemonDetailsDto
import com.example.obrio_test_task.data.remote.dto.PokemonResultsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResultsDto

    @GET("pokemon/{id}")
    suspend fun getSinglePokemon(
        @Path("id") pokemonId: Int
    ): PokemonDetailsDto

}