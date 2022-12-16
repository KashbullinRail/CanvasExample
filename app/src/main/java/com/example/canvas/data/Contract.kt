package com.example.canvas

import com.example.canvas.base.Event
import com.example.canvas.data.model.CanvasViewState
import com.example.canvas.domain.ToolItem
import com.example.canvas.data.settings.COLOR
import com.example.canvas.data.settings.SIZE
import com.example.canvas.data.settings.TOOLS


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
    data class OnSizeClicked(val index: Int) : UiEvent()
    data class OnToolsClick(val index: Int) : UiEvent()
    object OnToolbarClicked : UiEvent()
}

sealed class DataEvent : Event {
    data class OnSetDefaultTools(val tool: TOOLS, val color: COLOR, val size: SIZE) : DataEvent()
}
