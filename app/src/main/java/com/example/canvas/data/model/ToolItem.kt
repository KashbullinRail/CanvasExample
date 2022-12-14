package com.example.canvas.data.model

import androidx.annotation.ColorRes
import com.example.canvas.base.Item
import com.example.canvas.data.settings.COLOR
import com.example.canvas.data.settings.POINTS
import com.example.canvas.data.settings.SIZE
import com.example.canvas.data.settings.TOOLS


sealed class ToolItem : Item {

    data class ColorModel(@ColorRes val color: Int) : ToolItem()
    data class SizeModel(val size: Int): ToolItem()
    data class PointModel(val point: Int): ToolItem()
    data class ToolModel(
        val type: TOOLS,
        val selectedTool: TOOLS = TOOLS.NORMAL,
        val isSelected: Boolean = false,
        val selectedPoints: POINTS = POINTS.MEDIUM,
        val selectedSize: SIZE = SIZE.SMALL,
        val selectedColor: COLOR = COLOR.BLACK
    ) : ToolItem()

}