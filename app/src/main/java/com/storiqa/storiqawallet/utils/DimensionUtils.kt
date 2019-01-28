package com.storiqa.storiqawallet.utils

import com.storiqa.storiqawallet.App

fun convertPxToDp(px: Float): Float = px / App.density

fun convertDpToPx(dp: Float): Float = dp * App.density