package com.example.lab8

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val categoriesState by viewModel.categoriesState

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            categoriesState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            categoriesState.error != null -> {
                Text("Error occurred: ${categoriesState.error}", modifier = Modifier.align(Alignment.Center))
            }
            categoriesState.data != null -> {
                // Mostrar la pantalla de categorías
                CategoryScreen(categories = categoriesState.data!!, onCategoryClick = { category ->
                    // Navegar a la pantalla de recetas por categoría
                    navController.navigate("recipes/${category.strCategory}")
                })
            }
        }
    }
}

// Muestra la lista de categorías
@Composable
fun CategoryScreen(categories: List<Category>, onCategoryClick: (Category) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(categories) { category ->
            // Utiliza CategoryItem para mostrar cada categoría
            CategoryItem(category = category, onClick = { onCategoryClick(category) })
        }
    }
}

// Muestra cada elemento de categoría
@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = category.strCategory,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// Pantalla que muestra las recetas por categoría
@Composable
fun RecipesScreen(category: String, navController: NavController, viewModel: MainViewModel = viewModel()) {
    val recipesState by viewModel.recipesState

    LaunchedEffect(category) {
        viewModel.fetchRecipesByCategory(category)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            recipesState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            recipesState.error != null -> {
                Text("Error: ${recipesState.error}", modifier = Modifier.align(Alignment.Center))
            }
            recipesState.data != null -> { // Verifica que data no sea null
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(recipesState.data!!) { recipe ->
                        RecipeItem(recipe = recipe, onClick = {
                            navController.navigate("recipeDetail/${recipe.idMeal}")
                        })
                    }
                }
            }
            else -> {
                Text("No recipes available.", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


// Muestra cada elemento de receta
@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(recipe.strMealThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = recipe.strMeal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}