package com.andruhavuho.man.like.recipes.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andruhavuho.man.like.recipes.models.Recipe

@Entity
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0,
    var title: String = "",
    val imageUri: String? = null,
    val calories: Int = 0,
    val proteins: Int = 0,
    val fats: Int = 0,
    val carbs: Int = 0,
    val ingredients: String = "",
    var description: String = "",
    var createdByUser: Boolean = false,
    var categories: String = ""
) {
    fun toRecipe(givenId: Long? = null): Recipe {
        return Recipe(
            id = givenId ?: recipeId,
            title = title,
            imageUri = imageUri,
            createdByUser = createdByUser,
            categories = categories,
        )
    }

    fun contains(token: String): Boolean {
        return title.contains(token)
                || ingredients.contains(token)
                || description.contains(token)
                || categories.contains(token)
    }
}
