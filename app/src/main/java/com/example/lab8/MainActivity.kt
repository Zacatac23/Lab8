package com.example.lab8

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab8.ui.theme.MyRecipeAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRecipeAppTheme {
                // Creas el NavController
                val navController = rememberNavController()

                // Scaffold que contiene las pantallas
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    // Llamada a AppNavHost que controla la navegación
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

// Esta función debe estar fuera de MainActivity
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "categories") {
        // Pantalla inicial de categorías
        composable("categories") {
            RecipeScreen(navController = navController)
        }

        // Pantalla para mostrar recetas según la categoría seleccionada
        composable("recipes/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: return@composable
            RecipesScreen(category = category, navController = navController)
        }

        // Pantalla para mostrar detalles de una receta seleccionada
        composable("recipeDetail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: return@composable
            RecipeDetailScreen(recipeId = recipeId)
        }
    }
}
