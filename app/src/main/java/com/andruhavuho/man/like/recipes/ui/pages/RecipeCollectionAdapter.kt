package com.andruhavuho.man.like.recipes.ui.pages

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andruhavuho.man.like.recipes.models.Recipe

class RecipeCollectionAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    var currentPos: Int = -1
    var recipes: List<Recipe> = listOf()

    override fun getItemCount(): Int = recipes.size

    override fun createFragment(position: Int): Fragment {
        currentPos = position
        return RecipeInfoFragment.newInstance(recipes[currentPos].id)
    }
}