package com.andruhavuho.man.like.recipes

import android.app.Application
import androidx.room.Room
import com.andruhavuho.man.like.recipes.database.AppDatabase
import com.andruhavuho.man.like.recipes.database.RecipeViewModel

class App : Application() {

    lateinit var vm: RecipeViewModel

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database"
        ).fallbackToDestructiveMigration()
            .createFromAsset("recipes.db")
//            .allowMainThreadQueries()
            .build()
        vm = RecipeViewModel(db)
    }
}