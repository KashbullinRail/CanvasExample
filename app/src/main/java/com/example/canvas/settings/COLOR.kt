package com.example.canvas.settings

import androidx.annotation.ColorRes
import com.example.canvas.R

enum class COLOR(
    @ColorRes
    val value: Int
) {

    BLACK(R.color.black),
    RED(android.R.color.holo_red_dark),
    BLUE(R.color.design_default_color_primary_dark),
    GREEN(android.R.color.holo_green_dark),
    PINK(R.color.purple_200),
    TURQUOISE(R.color.teal_200),
    YELLOW(android.R.color.holo_orange_light),
    L_BLUE(android.R.color.holo_blue_bright),
    ORANGE(android.R.color.holo_orange_dark),
    WHITE(R.color.white);

    companion object {
        private val map = values().associateBy(COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}