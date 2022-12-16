package com.example.canvas.settings


enum class SIZE(
    val value: Int
) {
    SMALL(10),
    MINI(15),
    MEDIUM(24),
    LARGE(40),
    BIGLARGE(60);

    companion object {
        private val map = values().associateBy(SIZE::value)
        fun from(size: Int) = map[size] ?: SMALL
    }

}