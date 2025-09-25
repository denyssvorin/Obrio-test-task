package com.example.obrio_test_task.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonDetailsEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val image: String,
    val height: Int? = null,
    val weight: Int? = null,
    val isFavorite: Boolean = false,
    val lastUpdated: Long,
    val isDeleted: Boolean = false
)

