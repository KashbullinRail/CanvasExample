package com.example.canvas.settings

import androidx.annotation.ColorRes
import com.example.canvas.R

enum class COLOR(
    @ColorRes
    val value: Int
) {

    BLACK(R.color.black),
    RED(android.R.color.holo_red_dark),
    BLUE(android.R.color.holo_blue_dark),
    GRAY(android.R.color.darker_gray),
    GREEN(android.R.color.holo_green_dark),
    PINK(R.color.purple_200),
    REDL(android.R.color.holo_red_light),
    TURQUOISE(R.color.teal_200),
    PURPLE7(R.color.purple_700),
    ORANGEL(android.R.color.holo_orange_light),
    GREENL(android.R.color.holo_green_light),
    BLUEL(android.R.color.holo_blue_bright),
    PURPLE5(R.color.purple_500),
    ORANGE(android.R.color.holo_orange_dark),
    WHITE(R.color.white);

    companion object {
        private val map = values().associateBy(COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}