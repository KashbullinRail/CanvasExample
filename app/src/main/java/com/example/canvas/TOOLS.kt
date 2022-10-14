package com.example.canvas

import androidx.annotation.DrawableRes

enum class TOOLS(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.ic_horizontal_line),
    DASH(R.drawable.ic_dashed_line),
    //    SIZE(R.drawable.ic_baseline_brightness_1_24),
    PALETTE(R.drawable.ic_baseline_brightness_1_24)
}