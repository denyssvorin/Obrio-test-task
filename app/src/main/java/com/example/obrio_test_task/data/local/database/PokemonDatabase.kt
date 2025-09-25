package com.example.obrio_test_task.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.obrio_test_task.data.local.dao.PokemonDao
import com.example.obrio_test_task.data.local.entities.PokemonDetailsEntity

@Database(entities = [PokemonDetailsEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}