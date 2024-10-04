package com.example.lab8

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): RecipesResponse

    @GET("lookup.php")
    suspend fun getRecipeDetails(@Query("i") recipeId: String): RecipesResponse
}


// Configuración de Retrofit
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
