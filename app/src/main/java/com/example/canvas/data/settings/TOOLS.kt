package com.example.canvas.data.settings

import androidx.annotation.DrawableRes
import com.example.canvas.R


enum class TOOLS(
    @DrawableRes
    val value: Int
) {
    NORMAL(R.drawable.ic_baseline_curved_line),
    DASH(R.drawable.ic_dashed_line),
    LEFT(R.drawable.ic_baseline_left),
    CIRCLE(R.drawable.ic_baseline_circle_main),
    RECTANGLE(R.drawable.ic_baseline_check_box_outline_blank_24),
    LINE(R.drawable.ic_baseline_horizontal_rule_24),
    SPRAY(R.drawable.ic_baseline_spray_points),
//    TEXT(R.drawable.ic_baseline_text),
    RIGHT(R.drawable.ic_baseline_right),
    POINTS(R.drawable.ic_baseline_points_amount),
    SIZE(R.drawable.ic_baseline_size),
    PALETTE(R.drawable.ic_baseline_palette)
}