package com.example.canvas.mainscreen

import androidx.annotation.ColorRes
import com.example.canvas.base.Item
import com.example.canvas.settings.COLOR
import com.example.canvas.settings.SIZE
import com.example.canvas.settings.TOOLS

sealed class ToolItem : Item {
    data class ColorModel(@ColorRes val color: Int) : ToolItem()
    data class SizeModel(val size: Int): ToolItem()
    data class ToolModel(
        val type: TOOLS,
        val selectedTool: TOOLS = TOOLS.NORMAL,
        val isSelected: Boolean = false,
        val selectedSize: SIZE = SIZE.SMALL,
        val selectedColor: COLOR = COLOR.BLACK
    ) : ToolItem()
}