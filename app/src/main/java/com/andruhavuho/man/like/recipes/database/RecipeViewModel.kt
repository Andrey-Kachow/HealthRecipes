package com.andruhavuho.man.like.recipes.database

import androidx.lifecycle.ViewModel
import com.andruhavuho.man.like.recipes.models.Recipe
import com.andruhavuho.man.like.recipes.utils.searchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeViewModel(
    private val db: AppDatabase
) : ViewModel() {

    suspend fun updateRecipe(recipeEntity: RecipeEntity) {
        withContext(Dispatchers.Default) {
            db.recipeDao().updateRecipe(recipeEntity)
        }
    }

    suspend fun getRecipe(recipeId: Long): RecipeEntity {
        return withContext(Dispatchers.Default) {
            db.recipeDao().getRecipe(recipeId)
        }
    }

    suspend fun getSearchResults(query: String): MutableList<Recipe> {
        return withContext(Dispatchers.Default) {
            searchResults(query, db.recipeDao().getAll())
        }
    }

    suspend fun getRecipes(): MutableList<Recipe> {
        return withContext(Dispatchers.Default) {
            db.recipeDao()
                .getAll()
                .map { recipeEntity -> recipeEntity.toRecipe(recipeEntity.recipeId) }.toMutableList()
        }
    }

    suspend fun deleteRecipeFromDB(recipeId: Long) {
        withContext(Dispatchers.Default) {
            db.recipeDao().apply {
                delete(getRecipe(recipeId))
            }
        }
    }

    suspend fun insertNewEntity(newEntity: RecipeEntity): Long {
        return withContext(Dispatchers.Default) {
            db.recipeDao().insert(newEntity)
        }
    }

    suspend fun getRecipeEntitiesFromRecipes(recipes: List<Recipe>): List<RecipeEntity> {
        return withContext(Dispatchers.Default) {
            recipes.map { db.recipeDao().getRecipe(it.id) }
        }
    }
}