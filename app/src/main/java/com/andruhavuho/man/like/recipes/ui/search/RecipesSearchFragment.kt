package com.andruhavuho.man.like.recipes.ui.search

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andruhavuho.man.like.recipes.App
import com.andruhavuho.man.like.recipes.MainActivity
import com.andruhavuho.man.like.recipes.R
import com.andruhavuho.man.like.recipes.database.RecipeViewModel
import com.andruhavuho.man.like.recipes.models.Recipe
import com.andruhavuho.man.like.recipes.utils.searchResults
import kotlinx.coroutines.launch

class RecipesSearchFragment : Fragment(R.layout.fragment_recipes_search) {

    private lateinit var etRecipeSearch: EditText
    private lateinit var tvSearchResultCount: TextView
    private lateinit var rvSearchResultRecipes: AnimatedGridRecyclerView

    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var vm : RecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = (requireActivity().application as App).vm
        tvSearchResultCount = view.findViewById(R.id.tvSearchResultCount)

        setupCategoriesButtons(view)
        setupRecyclerView(view)

        etRecipeSearch = view.findViewById(R.id.etRecipeSearch)
        etRecipeSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })

        showCategoryRecipes()
    }

    private fun performSearch() {
        vm.viewModelScope.launch {
            showRecipes(vm.getSearchResults(etRecipeSearch.text.toString()))
        }
    }

    private fun setupCategoriesButtons(view: View) {
        val llCategoryButtons = view.findViewById<LinearLayout>(R.id.llCategoryButtons) as ViewGroup
        for (child in llCategoryButtons.children) {
            val button = child as Button
            button.setOnClickListener {
                showCategoryRecipes(button.text.toString())
            }
        }
    }

    private fun showCategoryRecipes(category: String = "Все") {
        vm.viewModelScope.launch {
            var recipes = vm.getRecipes()
            if (category != "Все") {
                recipes = recipes.filter { it.categories.contains(category) } as MutableList
            }
            showRecipes(recipes)
        }
    }

    private fun setupRecyclerView(view: View) {
        recipesAdapter = RecipesAdapter(requireContext()) { recipe ->
            val tempRecipes = recipesAdapter.recipes
            (requireActivity() as MainActivity).showRecipeCollectionFragment(recipe.id, tempRecipes)
        }
        rvSearchResultRecipes = view.findViewById(R.id.rvSearchResultRecipes)
        rvSearchResultRecipes.adapter = recipesAdapter
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvSearchResultRecipes.layoutManager = GridLayoutManager(context, 2)
        } else {
            rvSearchResultRecipes.layoutManager = GridLayoutManager(context, 4)
        }
    }

    private fun showRecipes(recipes: List<Recipe>) {

        val controller = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.gridlayout_animation_from_bottom)
        rvSearchResultRecipes.layoutAnimation = controller

        recipesAdapter.recipes = recipes
        recipesAdapter.notifyDataSetChanged()

        rvSearchResultRecipes.scheduleLayoutAnimation()
        updateNotesCount()
    }

    private fun updateNotesCount() {
        tvSearchResultCount.text = "Найдено рецептов: ${recipesAdapter.itemCount}"
    }
}
