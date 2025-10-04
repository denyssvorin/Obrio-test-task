package com.example.obrio_test_task.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.obrio_test_task.presentation.screens.details.PokemonDetailsScreen
import com.example.obrio_test_task.presentation.screens.list.PokemonListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenDestinations.PokemonList
    ) {
        composable<ScreenDestinations.PokemonList> {
            PokemonListScreen(
                onItemClick = { pokemonId: Int ->
                    navController.navigate(ScreenDestinations.PokemonDetails(pokemonId))
                }
            )
        }
        composable<ScreenDestinations.PokemonDetails> { backStackEntry ->
            val pokemonId = backStackEntry.toRoute<ScreenDestinations.PokemonDetails>()
            PokemonDetailsScreen(
                pokemonId = pokemonId.id,
                onBackClick = {
                    navController.navigateUp()
                })
        }
    }
}