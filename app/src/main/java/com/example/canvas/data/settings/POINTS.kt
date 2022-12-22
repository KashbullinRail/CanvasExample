package com.example.canvas.data.settings


enum class POINTS(
    val value: Int
) {
    FEW(30),
    MEDIUM(50),
    MANY(76),
    SOMANY(90);

    companion object {
        private val map = values().associateBy(POINTS::value)
        fun from(points: Int) = map[points] ?: MEDIUM
    }
}

