package com.andruhavuho.man.like.recipes.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.andruhavuho.man.like.recipes.database.RecipeEntity
import com.andruhavuho.man.like.recipes.models.Recipe
import java.io.IOException

@Throws(IOException::class)
fun getBitmapFromAsset(context: Context, filename: String): Bitmap? {
    return BitmapFactory.decodeStream(context.assets.open(filename))
}

fun searchResults(query: String, recipes: List<RecipeEntity>): MutableList<Recipe> {
    val results = mutableSetOf<Recipe>()
    query.split(" ", ", ")
        .map { it.trim() }
        .filter { it != "" }
        .forEach { token ->
            recipes.filter { it.contains(token) }
                .map { it.toRecipe() }
                .forEach { results.add(it) }
        }
    return results.toMutableList()
}

fun loadRecipeImage(createdByUser: Boolean, imageUri: String?, iv: ImageView, context: Context) {
    if (createdByUser) {
        //TODO ADD CUSTOM RECIPES IMAGE SUPPORT
    } else {
        imageUri?.let { uri ->
            getBitmapFromAsset(context, uri)?.let { bitmap ->
                iv.setImageBitmap(bitmap)
            }
        }
    }
}