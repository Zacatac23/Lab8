package com.example.lab8

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val recipeService: ApiService = RetrofitClient.apiService

    // Estado para categorías
    private val _categoriesState = mutableStateOf(RecipeState<List<Category>>())
    val categoriesState: State<RecipeState<List<Category>>> = _categoriesState

    // Estado para recetas
    private val _recipesState = mutableStateOf(RecipeState<List<Recipe>>())
    val recipesState: State<RecipeState<List<Recipe>>> = _recipesState

    // Estado para detalles de receta
    private val _recipeDetailState = mutableStateOf(RecipeDetailState())
    val recipeDetailState: State<RecipeDetailState> = _recipeDetailState

    // Función para obtener categorías
    fun fetchCategories() {
        viewModelScope.launch {
            _categoriesState.value = RecipeState(loading = true)
            try {
                val response = recipeService.getCategories()
                _categoriesState.value = RecipeState(
                    loading = false,
                    data = response.categories,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = RecipeState(
                    loading = false,
                    data = emptyList(),
                    error = "Error fetching categories: ${e.message}"
                )
            }
        }
    }

    // Función para obtener recetas por categoría
    fun fetchRecipesByCategory(category: String) {
        viewModelScope.launch {
            _recipesState.value = RecipeState(loading = true)
            try {
                val response = recipeService.getRecipesByCategory(category)
                _recipesState.value = RecipeState(
                    loading = false,
                    data = response.meals,
                    error = null
                )
            } catch (e: Exception) {
                _recipesState.value = RecipeState(
                    loading = false,
                    data = emptyList(),
                    error = "Error fetching recipes: ${e.message}"
                )
            }
        }
    }

    // Función para obtener detalles de una receta
    fun fetchRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            _recipeDetailState.value = RecipeDetailState(loading = true)
            try {
                val response = recipeService.getRecipeDetails(recipeId)
                _recipeDetailState.value = RecipeDetailState(
                    loading = false,
                    recipe = response.meals.firstOrNull(),
                    error = null
                )
            } catch (e: Exception) {
                _recipeDetailState.value = RecipeDetailState(
                    loading = false,
                    recipe = null,
                    error = "Error fetching recipe details: ${e.message}"
                )
            }
        }
    }

    // Clase de estado para recetas y categorías
    data class RecipeState<T>(
        val loading: Boolean = false,
        val data: T? = null,
        val error: String? = null
    )

    // Clase de estado para detalles de la receta
    data class RecipeDetailState(
        val loading: Boolean = false,
        val recipe: Recipe? = null,
        val error: String? = null
    )
}