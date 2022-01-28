package com.andruhavuho.man.like.recipes.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.andruhavuho.man.like.recipes.App
import com.andruhavuho.man.like.recipes.R
import com.andruhavuho.man.like.recipes.database.RecipeEntity
import com.andruhavuho.man.like.recipes.database.RecipeViewModel
import com.andruhavuho.man.like.recipes.utils.loadRecipeImage
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

// Instances of this class are fragments representing a single
// object in our collection.
class RecipeInfoFragment : Fragment(R.layout.fragment_recipe_info) {

    private lateinit var vm: RecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        vm = (requireActivity().application as App).vm

        var currentRecipeId = 0L

        arguments?.getLong(RECIPE_ID)?.takeIf { it > 0 }?.let {
            currentRecipeId = it
        } ?: run {
            requireActivity().onBackPressed()
        }

        vm.viewModelScope.launch {
            val entity = vm.getRecipe(currentRecipeId)

            view.findViewById<ImageView>(R.id.ivBigRecipeImage).apply {
                loadRecipeImage(entity.createdByUser, entity.imageUri, this, requireContext())
            }
            view.findViewById<ImageView>(R.id.ivReturnBackArrow).setOnClickListener {
                requireActivity().onBackPressed()
            }
            view.findViewById<TextView>(R.id.tvRecipeTitle).apply {
                text = entity.title
            }
            view.findViewById<TextView>(R.id.tvRecipeCalories).apply {
                text = entity.calories.toString()
            }
            view.findViewById<TextView>(R.id.tvRecipeProteins).apply {
                text = entity.proteins.toString()
            }
            view.findViewById<TextView>(R.id.tvRecipeFats).apply {
                text = entity.fats.toString()
            }
            view.findViewById<TextView>(R.id.tvRecipeCarbohydrates).apply {
                text = entity.carbs.toString()
            }
            view.findViewById<TextView>(R.id.tvRecipeIngredients).apply {
                text = entity.ingredients
            }
            view.findViewById<TextView>(R.id.tvHowToCook).apply {
                text = entity.description
            }
            view.findViewById<TextView>(R.id.tvCategories).apply {
                text = "Категории: ${entity.categories}"
            }
        }

    }

    companion object {

        const val RECIPE_ID = "RECIPE_ID"

        fun newInstance(recipeId: Long): RecipeInfoFragment {
            return RecipeInfoFragment().apply {
                arguments = bundleOf(RECIPE_ID to recipeId)
            }
        }
    }
}