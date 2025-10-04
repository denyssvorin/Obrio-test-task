package com.example.obrio_test_task.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.obrio_test_task.data.local.database.PokemonDatabase
import com.example.obrio_test_task.data.mappers.toPokemonEntity
import com.example.obrio_test_task.data.mappers.toPokemonModel
import com.example.obrio_test_task.data.remote.api.PokemonApi
import com.example.obrio_test_task.data.repository.pagination.PokemonRemoteMediator
import com.example.obrio_test_task.domain.models.PokemonModel
import com.example.obrio_test_task.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
    private val db: PokemonDatabase,
) : PokemonRepository {

    val dao = db.pokemonDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                enablePlaceholders = true,
            ),
            remoteMediator = PokemonRemoteMediator(
                api = api,
                db = db
            ),
            pagingSourceFactory = {
                dao.getAllPokemons()
            }
        ).flow
            .map { pagingData ->
                pagingData.map { entity ->
                    entity.toPokemonModel()
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPokemonById(id: Int): Flow<PokemonModel> =
        dao.getPokemonById(id)
            .flatMapLatest { local ->
                if (local != null && local.weight != null) {
                    flowOf(local.toPokemonModel())
                } else {
                    flow {
                        val remote = api.getSinglePokemon(id)
                        dao.savePokemon(
                            remote.toPokemonEntity(
                                isFavorite = local?.isFavorite == true,
                                lastUpdated = System.currentTimeMillis()
                            )
                        )
                    }
                }
            }
            .flowOn(Dispatchers.IO)

    override fun getFavoritesCount(): Flow<Int> =
        dao.getPokemonFavoritesCount()

    override suspend fun updatePokemon(pokemonModel: PokemonModel) = withContext(Dispatchers.IO) {
        try {
            dao.updatePokemon(pokemonModel.toPokemonEntity())
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override suspend fun deletePokemon(pokemonModel: PokemonModel) = withContext(Dispatchers.IO) {
        try {
            dao.deletePokemon(pokemonModel.toPokemonEntity())
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }
}