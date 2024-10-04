package com.example.lab8

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun RecipeDetailScreen(recipeId: String, viewModel: MainViewModel = viewModel()) {
    val recipeDetailState by viewModel.recipeDetailState

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetails(recipeId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            recipeDetailState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            recipeDetailState.error != null -> {
                Text("Error: ${recipeDetailState.error}", modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                recipeDetailState.recipe?.let { recipe ->
                    val scrollState = rememberScrollState() // Crear el estado de desplazamiento
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(scrollState) // Aplicar el desplazamiento vertical
                    ) {
                        Text(recipe.strMeal, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Image(
                            painter = rememberAsyncImagePainter(recipe.strMealThumb),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                        )
                        Text(recipe.strInstructions, modifier = Modifier.padding(top = 16.dp))
                    }
                }
            }
        }
    }
}
