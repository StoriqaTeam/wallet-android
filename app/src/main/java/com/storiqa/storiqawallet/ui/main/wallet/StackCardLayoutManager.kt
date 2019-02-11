package com.storiqa.storiqawallet.ui.main.wallet

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.R


class StackCardLayoutManager : RecyclerView.LayoutManager() {
    private val addedChildren: List<View>
        get() = (0 until childCount).map { getChildAt(it) ?: throw NullPointerException() }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
            RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.MATCH_PARENT)

    override fun isAutoMeasureEnabled(): Boolean = true

    override fun onLayoutChildren(
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State
    ) {
        if (state.itemCount == 0) {
            return
        }

        detachAndScrapAttachedViews(recycler)

        for (i in 0 until state.itemCount) {
            val view = recycler.getViewForPosition(i)
            measureChild(view, 0, 0)
            addView(view)
            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            val left = layoutParams.marginStart
            val top = (view.measuredHeight * i * 0.25).toInt()
            val right = view.measuredWidth + layoutParams.marginEnd
            val bottom = top + view.measuredHeight
            layoutDecorated(view, left, top, right, bottom)
            view.setTag(InitializedPosition.TOP.key, top)
        }
    }

    override fun canScrollVertically(): Boolean = true

    override fun scrollVerticallyBy(
            dy: Int,
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State
    ): Int = dy.also { deltaY ->
        if (childCount == 0) {
            return@also
        }

        addedChildren.forEachIndexed { index, view ->
            val initializedTop = view.getTag(InitializedPosition.TOP.key) as Int
            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            val left = layoutParams.marginStart
            val top = Math.min(Math.max((view.top - deltaY), index * 30), initializedTop)
            val right = view.measuredWidth + layoutParams.marginEnd
            val bottom = top + view.measuredHeight
            layoutDecorated(view, left, top, right, bottom)
        }
    }

    private enum class InitializedPosition(val key: Int) {
        TOP(R.integer.top)
    }
}
