package com.example.obrio_test_task.di

import android.content.Context
import androidx.room.Room
import com.example.obrio_test_task.data.local.database.PokemonDatabase
import com.example.obrio_test_task.data.remote.api.PokemonApi
import com.example.obrio_test_task.data.repository.PokemonRepositoryImpl
import com.example.obrio_test_task.domain.repository.PokemonRepository
import com.example.obrio_test_task.presentation.utils.imagememorycache.ImageMemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(
        retrofit: Retrofit,
    ): PokemonApi = retrofit.create(PokemonApi::class.java)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokemonApi, database: PokemonDatabase): PokemonRepository =
        PokemonRepositoryImpl(api = api, db = database)

    @Provides
    @Singleton
    fun provideImageMemoryCache(): ImageMemoryCache = ImageMemoryCache()
}