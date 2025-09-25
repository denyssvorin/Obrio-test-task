package com.example.obrio_test_task.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.obrio_test_task.data.local.entities.PokemonDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table WHERE isDeleted = 0 ORDER BY id ASC")
    fun getAllPokemons(): PagingSource<Int, PokemonDetailsEntity>

    @Query("SELECT * FROM pokemon_table WHERE id LIKE :pokemonId")
    fun getPokemonById(pokemonId: Int): Flow<PokemonDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePokemons(pokemon: List<PokemonDetailsEntity>)

    @Upsert
    suspend fun savePokemon(pokemon: PokemonDetailsEntity)

    @Update
    suspend fun updatePokemon(pokemon: PokemonDetailsEntity)

    @Delete
    suspend fun deletePokemon(pokemon: PokemonDetailsEntity)

    @Query("SELECT MAX(lastUpdated) FROM pokemon_table WHERE isDeleted = 0")
    suspend fun lastUpdateTime(): Long?

    @Query("DELETE FROM pokemon_table WHERE isDeleted = 0")
    suspend fun clearAllPokemons()

    @Query("SELECT COUNT(*) FROM pokemon_table")
    suspend fun getTotalCount(): Int

    @Query("SELECT COUNT(*) FROM pokemon_table WHERE isFavorite = 1 AND isDeleted = 0")
    fun getPokemonFavoritesCount(): Flow<Int>
}