package com.andruhavuho.man.like.recipes.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andruhavuho.man.like.recipes.R
import com.andruhavuho.man.like.recipes.models.Recipe
import com.andruhavuho.man.like.recipes.utils.getBitmapFromAsset
import com.andruhavuho.man.like.recipes.utils.loadRecipeImage

class RecipesAdapter(
    val context: Context,
    private val clickListener: (recipe: Recipe) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var recipes: List<Recipe> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position], clickListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivRecipeItemImage = itemView.findViewById<ImageView>(R.id.ivRecipeItemImage)
        private val tvRecipeItemTitle = itemView.findViewById<TextView>(R.id.tvRecipeItemTitle)

        fun bind(recipe: Recipe, clickListenerFunction: (recipe: Recipe) -> Unit) {

            tvRecipeItemTitle.text = recipe.title
            loadRecipeImage(recipe.createdByUser, recipe.imageUri, ivRecipeItemImage, context)

            itemView.setOnClickListener {
                clickListenerFunction.invoke(recipe)
            }
        }
    }
}
