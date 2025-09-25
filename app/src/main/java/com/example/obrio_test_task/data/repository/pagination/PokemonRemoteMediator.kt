package com.example.obrio_test_task.data.repository.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.obrio_test_task.data.local.database.PokemonDatabase
import com.example.obrio_test_task.data.local.entities.PokemonDetailsEntity
import com.example.obrio_test_task.data.mappers.toPokemonEntity
import com.example.obrio_test_task.data.remote.api.PokemonApi
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val api: PokemonApi,
    private val db: PokemonDatabase,
) : RemoteMediator<Int, PokemonDetailsEntity>() {

    val dao = db.pokemonDao()

    override suspend fun initialize(): InitializeAction {
        val lastUpdatedTime = dao.lastUpdateTime() ?: 0L
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - lastUpdatedTime <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonDetailsEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val key = db.withTransaction {
                        dao.getTotalCount()
                    }

                    if (key == 0) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    key
                }

            }

            val pokemonResultsDtoList = api.getPokemons(
                limit = state.config.pageSize,
                offset = loadKey
            ).results

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.pokemonDao().clearAllPokemons()
                }
                val pokemonEntities =
                    pokemonResultsDtoList.map { it.toPokemonEntity(lastUpdated = System.currentTimeMillis()) }

                dao.savePokemons(pokemonEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = pokemonResultsDtoList.isEmpty()
            )
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}