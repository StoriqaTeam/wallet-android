package com.storiqa.storiqawallet.extensions

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.storiqa.storiqawallet.R

fun TabLayout.setup() {
    for (i in 0 until tabCount) {
        val tab = getTabAt(i)
        if (tab != null) {
            setupTab(context, tab, i)
        }
        val layout = (getChildAt(0) as LinearLayout).getChildAt(i) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layout.layoutParams = layoutParams
    }

    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            val tabTextView = tab.customView as TextView?
            if (tabTextView != null)
                onTabSelected(tabTextView)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            val tabTextView = tab.customView as TextView?
            if (tabTextView != null)
                onTabUnselected(tabTextView)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
        }
    })
}

private fun setupTab(context: Context, tab: TabLayout.Tab, pos: Int) {
    val tabTextView = TextView(context)
    tab.customView = tabTextView
    tabTextView.apply {
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        setPadding(0, 0, 0, 20)
        gravity = Gravity.BOTTOM
        text = tab.text
    }

    if (pos == 0)
        onTabSelected(tabTextView)
    else
        onTabUnselected(tabTextView)
}

private fun onTabSelected(textView: TextView) {
    textView.typeface = ResourcesCompat.getFont(textView.context, R.font.montserrat_bold)
    textView.textSize = 24f
    textView.setTextColor(ContextCompat.getColor(textView.context, R.color.tab_selected_text))
}

private fun onTabUnselected(textView: TextView) {
    textView.typeface = ResourcesCompat.getFont(textView.context, R.font.montserrat_medium)
    textView.textSize = 16f
    textView.setTextColor(ContextCompat.getColor(textView.context, R.color.tab_unselected_text))
}