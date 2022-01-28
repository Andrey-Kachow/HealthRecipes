package com.andruhavuho.man.like.recipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.andruhavuho.man.like.recipes.database.RecipeEntity
import com.andruhavuho.man.like.recipes.models.Recipe
import com.andruhavuho.man.like.recipes.ui.search.RecipesSearchFragment
import com.andruhavuho.man.like.recipes.ui.pages.RecipeCollectionFragment
import com.google.android.material.snackbar.Snackbar
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<RecipesSearchFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    fun showRecipeCollectionFragment(recipeId: Long, recipes: List<Recipe>) {
        showFragment(RecipeCollectionFragment.newInstance(recipeId, recipes))
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }
}