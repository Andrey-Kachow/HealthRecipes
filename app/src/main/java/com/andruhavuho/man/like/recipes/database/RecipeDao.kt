package com.andruhavuho.man.like.recipes.database

import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg recipes: RecipeEntity)

    @Delete
    fun delete(recipe: RecipeEntity)

    @Update
    fun updateRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM RecipeEntity")
    fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE :recipeId = recipeId")
    fun getRecipe(recipeId: Long): RecipeEntity

}