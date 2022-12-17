package com.example.canvas.data.settings

import androidx.annotation.DrawableRes
import com.example.canvas.R


enum class TOOLS(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.ic_baseline_horizontal_rule_24),
    CIRCLE(R.drawable.ic_baseline_adjust_24),
    RECTANGLE(R.drawable.ic_baseline_check_box_outline_blank_24),
    DASH(R.drawable.ic_dashed_line),
    SIZE(R.drawable.ic_baseline_zoom_out_map_24),
    PALETTE(R.drawable.ic_baseline_brightness_1_24)
}