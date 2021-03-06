package com.company.test_app.presentation.adapter.decoration

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import com.company.test_app.presentation.adapter.controller.BaseSwipeController

class SwipeItemDecoration(
    private val swipeController: BaseSwipeController
) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        swipeController.onDraw(c)
    }
}