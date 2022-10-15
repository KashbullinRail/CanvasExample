package com.example.canvas

import com.example.canvas.base.Event
import com.example.canvas.feature.model.CanvasViewState
import com.example.canvas.mainscreen.ToolItem
import com.example.canvas.settings.COLOR
import com.example.canvas.settings.SIZE
import com.example.canvas.settings.TOOLS

data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorList: List<ToolItem.ColorModel>,
    val sizeList: List<ToolItem.SizeModel>,
    val canvasViewState: CanvasViewState,
    val isPaletteVisible: Boolean,
    val isBrushSizeChangerVisible: Boolean,
    val isToolsVisible: Boolean
)

sealed class UiEvent : Event {
    data class OnPaletteClicked(val index: Int) : UiEvent()
    data class OnColorClick(val index: Int) : UiEvent()
    data class OnSizeClick(val index: Int) : UiEvent()
    data class OnToolsClick(val index: Int) : UiEvent()
    object OnDrawViewClicked : UiEvent()
    object OnToolbarClicked : UiEvent()
}

sealed class DataEvent : Event {
    data class OnSetDefaultTools(val tool: TOOLS, val color: COLOR, val size: SIZE) : DataEvent()
}
