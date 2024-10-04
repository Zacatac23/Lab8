package com.example.lab8

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
) {

}

data class CategoriesResponse(val categories: List<Category>)

data class Recipe(
    val idMeal: String,         // El identificador único de la receta
    val strMeal: String,        // El nombre de la receta
    val strMealThumb: String,   // La URL de la imagen de la receta
    val strInstructions: String // Las instrucciones para la receta
)


data class RecipesResponse(val meals: List<Recipe>)
