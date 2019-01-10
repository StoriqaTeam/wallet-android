package com.storiqa.storiqawallet.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.view.View


class SmallCircle(context: Context,
                  private val radius: Int,
                  @ColorRes private val colorDefault: Int,
                  @ColorRes private val colorHighlight: Int) : View(context) {

    var isHighlight = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        minimumHeight = radius * 2
        minimumWidth = radius * 2
        isSaveEnabled = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null)
            return

        val w = this.width
        val h = this.height

        val ox = (w / 2).toFloat()
        val oy = (h / 2).toFloat()

        canvas.drawCircle(ox, oy, radius.toFloat(), getFill())
    }

    private fun getFill(): Paint {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = if (isHighlight) resources.getColor(colorHighlight) else resources.getColor(colorDefault)
        p.style = Paint.Style.FILL
        return p
    }
}