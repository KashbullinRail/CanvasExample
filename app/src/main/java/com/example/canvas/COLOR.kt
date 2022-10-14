package com.example.canvas

import androidx.annotation.ColorRes

enum class COLOR(
    @ColorRes
    val value: Int
) {

    BLACK(R.color.black),
    RED(R.color.purple_500),
    BLUE(R.color.purple_700),
    GREEN(R.color.purple_200),
    PINK(R.color.teal_700);

    companion object {
        private val map = values().associateBy(COLOR::value)
        fun from(color: Int) = map[color] ?: BLACK
    }
}