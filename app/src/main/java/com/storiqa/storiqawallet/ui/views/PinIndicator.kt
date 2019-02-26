package com.storiqa.storiqawallet.ui.views

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.storiqa.storiqawallet.R


class PinIndicator
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    private var pinLength = 0
    private var circleRadius = 5
    private var colorDefault = android.R.color.darker_gray
    private var colorHighlight = android.R.color.holo_orange_light
    private var gap = 0

    private var circles = ArrayList<SmallCircle>()

    var highlightCircleCount = 0
        set(value) {
            when {
                value == 0 -> circles.forEach { it.isHighlight = false }
                value > field -> {
                    for (i in 0..field)
                        circles[i].isHighlight = true
                }
                value < field -> {
                    for (i in pinLength - 1 downTo value)
                        circles[i].isHighlight = false
                }
            }

            field = value
        }

    init {
        orientation = HORIZONTAL

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PinIndicator)

        colorDefault = attributes.getResourceId(R.styleable.PinIndicator_colorDefault, colorDefault)
        colorHighlight = attributes.getResourceId(R.styleable.PinIndicator_colorHighlight, colorHighlight)
        circleRadius = attributes.getDimensionPixelSize(R.styleable.PinIndicator_radius, circleRadius)
        gap = attributes.getDimensionPixelSize(R.styleable.PinIndicator_gap, gap)
        pinLength = attributes.getInteger(R.styleable.PinIndicator_pinLength, resources.getInteger(R.integer.PIN_LENGTH))

        attributes.recycle()

        val size = 2 * circleRadius

        for (i: Int in 1..pinLength) {
            val circle = SmallCircle(context, circleRadius, colorDefault, colorHighlight)
            val params = LayoutParams(size, size).apply {
                leftMargin = gap / 2
                rightMargin = gap / 2
            }
            circle.layoutParams = params
            circles.add(circle)
            addView(circle)
        }
    }

    fun startErrorAnimation() {
        ObjectAnimator
                .ofFloat(this, "translationX", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
                .start()
    }
}