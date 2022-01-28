package com.andruhavuho.man.like.recipes.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class AnimatedGridRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is GridLayoutManager) {
            super.setLayoutManager(layout)
        } else {
            throw ClassCastException("This recyclerview should use grid layout manager as the layout manager")
        }
    }

    override fun attachLayoutAnimationParameters(
        child: View?,
        params: ViewGroup.LayoutParams,
        index: Int,
        count: Int
    ) {
        if (adapter != null && layoutManager is GridLayoutManager) {
            val columns = (layoutManager as GridLayoutManager).spanCount
            var animationParams =
                params.layoutAnimationParameters as GridLayoutAnimationController.AnimationParameters?

            if (animationParams == null) {
                animationParams = GridLayoutAnimationController.AnimationParameters()
                params.layoutAnimationParameters = animationParams
            }

            animationParams.apply {
                this.count = count
                this.index = index
                columnsCount = columns
                rowsCount = count / columns
                val invertedIndex = count - 1 - index
                column = columns - 1 - invertedIndex % columns
                row = rowsCount - 1 - invertedIndex / columns
            }
        } else {
            Snackbar.make(this, "NOPE", Snackbar.LENGTH_LONG).show()
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }
}