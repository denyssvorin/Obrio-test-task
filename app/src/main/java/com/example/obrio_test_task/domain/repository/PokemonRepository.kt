package com.example.obrio_test_task.domain.repository

import androidx.paging.PagingData
import com.example.obrio_test_task.domain.models.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<PagingData<PokemonModel>>
    fun getPokemonByIdFromServer(id: Int): Flow<PokemonModel>
    fun getFavoritesCount(): Flow<Int>
    suspend fun updatePokemon(pokemonModel: PokemonModel)
    suspend fun deletePokemon(pokemonModel: PokemonModel)
}