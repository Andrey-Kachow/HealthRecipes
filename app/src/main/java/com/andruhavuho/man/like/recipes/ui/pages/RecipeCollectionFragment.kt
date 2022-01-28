package com.andruhavuho.man.like.recipes.ui.pages

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.andruhavuho.man.like.recipes.App
import com.andruhavuho.man.like.recipes.R
import com.andruhavuho.man.like.recipes.database.RecipeEntity
import com.andruhavuho.man.like.recipes.database.RecipeViewModel
import com.andruhavuho.man.like.recipes.models.Recipe
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RecipeCollectionFragment : Fragment(R.layout.collection_food) {

    private var currentRecipeId: Long = 0L

    private lateinit var vm: RecipeViewModel
    private lateinit var recipeCollectionAdapter: RecipeCollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        vm = (requireActivity().application as App).vm

        arguments?.getLong(CURRENT_RECIPE_ID)?.takeIf { it > 0 }?.let {
            currentRecipeId = it
        } ?: run {
            backPressWithSomethingWentWrong(view)
        }
        recipeCollectionAdapter = RecipeCollectionAdapter(this)
        viewPager = view.findViewById(R.id.vp2FoodFragmentsPager)
        viewPager.adapter = recipeCollectionAdapter

        val restoredRecipes = arguments?.getParcelableArray(RECIPES_FOR_ADAPTER)?.toList()?.map { it as Recipe }
        vm.viewModelScope.launch {
            recipeCollectionAdapter.recipes = restoredRecipes ?: listOf()
            showCurrentFragment(currentRecipeId)
        }
    }

    private fun showCurrentFragment(recipeId: Long) {
        recipeCollectionAdapter.apply {
            var i = 0
            for (recipe in recipes) {
                if (recipe.id == recipeId) {
                    break
                }
                i++
            }
            viewPager.setCurrentItem(i, false)
            notifyDataSetChanged()
        }
    }

    private fun backPressWithSomethingWentWrong(view: View) {
        Snackbar.make(view, "Что-то пошло не так", Snackbar.LENGTH_LONG).show()
        requireActivity().onBackPressed()
    }

    companion object {
        const val RECIPES_FOR_ADAPTER = "RECIPES_FOR_ADAPTER"
        const val CURRENT_RECIPE_ID = "CURRENT_RECIPE_ID"

        fun newInstance(currentRecipeId: Long, recipes: List<Recipe>): RecipeCollectionFragment {
            return RecipeCollectionFragment().apply {
                arguments = bundleOf(
                    RECIPES_FOR_ADAPTER to recipes.toTypedArray(),
                    CURRENT_RECIPE_ID to currentRecipeId
                )
            }
        }
    }
}
