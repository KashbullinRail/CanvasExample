package com.example.canvas

data class ViewState(
    val toolsList: List<ToolItem.ToolModel>,
    val colorList: List<ToolItem.ColorModel>,
//    val sizeList: List<ToolItem.SizeModel>,
    val canvasViewState: CanvasViewState,
    val isPaletteVisible: Boolean,
//    val isBrushSizeChangerVisible: Boolean,
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
    data class OnSetDefaultTools(val tool: TOOLS, val color: COLOR) : DataEvent()
}
