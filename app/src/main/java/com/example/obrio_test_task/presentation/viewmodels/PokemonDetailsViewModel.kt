package com.example.obrio_test_task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obrio_test_task.domain.repository.PokemonRepository
import com.example.obrio_test_task.presentation.models.PokemonUiModel
import com.example.obrio_test_task.presentation.state.PokemonUiState
import com.example.obrio_test_task.presentation.utils.mappers.toPokemonModel
import com.example.obrio_test_task.presentation.utils.mappers.toPokemonUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _pokemonId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<PokemonUiState> = _pokemonId
        .filterNotNull()
        .flatMapLatest { id ->
            repository.getPokemonById(id)
                .map {
                    PokemonUiState(isLoading = false, pokemon = it.toPokemonUiModel()) }
                .onStart {
                    emit(PokemonUiState(isLoading = true)) }
                .catch { e ->
                    e.printStackTrace()
                    emit(PokemonUiState(isLoading = false, error = e.message)) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PokemonUiState())


    fun setPokemonId(id: Int) {
        _pokemonId.value = id
    }

    fun onFavoriteClick(pokemonUiModel: PokemonUiModel) = viewModelScope.launch {
        val updatedPokemon = pokemonUiModel.copy(isFavorite = !pokemonUiModel.isFavorite)
        repository.updatePokemon(updatedPokemon.toPokemonModel())
    }
}