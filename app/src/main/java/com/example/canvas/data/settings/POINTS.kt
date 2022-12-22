package com.example.canvas.data.settings

enum class POINTS(
    val value: Int
) {
    FEW(30),
    MEDIUM(50),
    MANY(75),
    SOMANY(100);

    companion object {
        private val map = POINTS.values().associateBy(POINTS::value)
        fun from(points: Int) = map[points] ?: POINTS.MEDIUM
    }
}

