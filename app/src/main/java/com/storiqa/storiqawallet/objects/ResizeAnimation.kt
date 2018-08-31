package com.storiqa.storiqawallet.objects

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class ResizeAnimation(internal var view: View,
                      private val targetHeight: Int,
                      private val startHeight: Int,
                      private val targetWidth: Int,
                      private val startWidth: Int) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = (startHeight+(targetHeight - startHeight) * interpolatedTime).toInt()
        val newWidth = (startWidth + (targetWidth - startWidth) * interpolatedTime).toInt()
        view.layoutParams.height = newHeight
        view.layoutParams.width = newWidth
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}