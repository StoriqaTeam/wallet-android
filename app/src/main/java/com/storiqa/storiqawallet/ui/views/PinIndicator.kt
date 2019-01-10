package com.storiqa.storiqawallet.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.storiqa.storiqawallet.R


class PinIndicator : LinearLayout {

    private var pinLength = 0
    private var circleRadius = 5
    private var colorDefault = android.R.color.darker_gray
    private var colorHighlight = android.R.color.holo_blue_dark
    private var gap = 0

    private var circles = ArrayList<SmallCircle>()

    var highlightCircleCount = 0
        set(value) {
            when {
                value == 0 -> circles.forEach { it.isHighlight = false }
                value > field -> circles[field].isHighlight = true
                value < field -> circles[value].isHighlight = false
            }

            field = value
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = HORIZONTAL

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PinIndicator)

        colorDefault = attributes.getResourceId(R.styleable.PinIndicator_colorDefault, colorDefault)
        colorHighlight = attributes.getResourceId(R.styleable.PinIndicator_colorHighlight, colorHighlight)
        circleRadius = attributes.getDimensionPixelSize(R.styleable.PinIndicator_radius, circleRadius)
        gap = attributes.getDimensionPixelSize(R.styleable.PinIndicator_gap, gap)
        pinLength = attributes.getInteger(R.styleable.PinIndicator_pinLength, resources.getInteger(R.integer.PIN_LENGTH))

        attributes.recycle()

        //pinLength = resources.getInteger(R.integer.PIN_LENGTH)

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
}