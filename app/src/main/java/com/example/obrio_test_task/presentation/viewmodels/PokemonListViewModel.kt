package com.example.obrio_test_task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.obrio_test_task.domain.models.PokemonModel
import com.example.obrio_test_task.domain.repository.PokemonRepository
import com.example.obrio_test_task.presentation.models.PokemonUiModel
import com.example.obrio_test_task.presentation.utils.mappers.toPokemonModel
import com.example.obrio_test_task.presentation.utils.mappers.toPokemonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    val pokemonData: Flow<PagingData<PokemonUiModel>> = repository.getPokemons()
        .map { pagingData: PagingData<PokemonModel> ->
            pagingData.map { pokemonModel ->
                pokemonModel.toPokemonUiModel()
            }
        }
        .catch { e ->
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
        .cachedIn(viewModelScope)

    val pokemonFavoritesCount: StateFlow<Int> =
        repository.getFavoritesCount()
            .catch { e ->
                e.printStackTrace()
                if (e is CancellationException) throw e
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)


    private val _navigateToPokemon = MutableSharedFlow<Int>()
    val navigateToPokemon: Flow<Int> = _navigateToPokemon.distinctUntilChanged()

    fun onPokemonClick(id: Int) {
        viewModelScope.launch {
            _navigateToPokemon.emit(id)
        }
    }

    fun onFavoriteClick(pokemonUiModel: PokemonUiModel) = viewModelScope.launch {
        val updatedPokemon = pokemonUiModel.copy(isFavorite = !pokemonUiModel.isFavorite)
        repository.updatePokemon(updatedPokemon.toPokemonModel())
    }

    fun onDeleteClick(pokemonUiModel: PokemonUiModel) = viewModelScope.launch {
//        repository.deletePokemon(pokemonUiModel.toPokemonModel())

        val updatedPokemon = pokemonUiModel.copy(isDeleted = true)
        repository.updatePokemon(updatedPokemon.toPokemonModel())
    }
}