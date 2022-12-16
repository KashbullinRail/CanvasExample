package com.example.canvas.settings

import androidx.annotation.DrawableRes
import com.example.canvas.R


enum class TOOLS(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.ic_horizontal_line),
    DASH(R.drawable.ic_dashed_line),
    SIZE(R.drawable.ic_baseline_zoom_out_map_24),
    PALETTE(R.drawable.ic_baseline_brightness_1_24)
}