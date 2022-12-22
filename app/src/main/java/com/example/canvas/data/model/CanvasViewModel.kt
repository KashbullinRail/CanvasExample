package com.example.canvas.data.model

import com.example.canvas.*
import com.example.canvas.base.BaseViewModel
import com.example.canvas.base.Event
import com.example.canvas.domain.ToolItem
import com.example.canvas.data.settings.COLOR
import com.example.canvas.data.settings.POINTS
import com.example.canvas.data.settings.SIZE
import com.example.canvas.data.settings.TOOLS


class CanvasViewModel : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState =
        ViewState(
            colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
            toolsList = enumValues<TOOLS>().map { ToolItem.ToolModel(it) },
            sizeList = enumValues<SIZE>().map { ToolItem.SizeModel(it.value) },
            pointList = enumValues<POINTS>().map { ToolItem.PointModel(it.value) },
            isPaletteVisible = false,
            isBrushSizeChangerVisible = false,
            isSprayPointsChangerVisible = false,
            canvasViewState = CanvasViewState(
                color = COLOR.GREEN,
                size = SIZE.SMALL,
                points = POINTS.MEDIUM,
                tools = TOOLS.NORMAL
            ),
            isToolsVisible = false
        )

    init {
        processDataEvent(
            DataEvent.OnSetDefaultTools(
                tool = TOOLS.NORMAL,
                color = COLOR.GREEN,
                size = SIZE.SMALL,
                points = POINTS.MEDIUM
            )
        )
    }

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible,
                    isPaletteVisible = false,
                    isBrushSizeChangerVisible = false,
                    isSprayPointsChangerVisible = false
                )
            }

            is UiEvent.OnToolsClick -> {
                when (event.index) {
                    TOOLS.PALETTE.ordinal -> {
                        return previousState.copy(
                            isPaletteVisible = !previousState.isPaletteVisible
                        )
                    }
                    TOOLS.SIZE.ordinal -> {
                        return previousState.copy(
                            isBrushSizeChangerVisible = !previousState.isBrushSizeChangerVisible
                        )
                    }
                    TOOLS.POINTS.ordinal -> {
                        return previousState.copy(
                            isSprayPointsChangerVisible = !previousState.isSprayPointsChangerVisible
                        )
                    }
                    else -> {

                        val toolsList = previousState.toolsList.mapIndexed() { index, model ->
                            if (index == event.index) {
                                model.copy(isSelected = true)
                            } else {
                                model.copy(isSelected = false)
                            }
                        }

                        return previousState.copy(
                            toolsList = toolsList,
                            canvasViewState = previousState.canvasViewState
                                .copy(tools = TOOLS.values()[event.index])
                        )
                    }
                }
            }

            is UiEvent.OnPaletteClicked -> {
                val selectedColor = COLOR.values()[event.index]

                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.PALETTE) {
                        it.copy(selectedColor = selectedColor)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(color = selectedColor)
                )
            }

            is UiEvent.OnSizeClicked -> {
                val selectedSize = SIZE.values()[event.index]

                val toolsList = previousState.toolsList.map {
                    if (it.type == TOOLS.SIZE) {
                        it.copy(selectedSize = selectedSize)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(size = selectedSize)
                )
            }
            is UiEvent.OnPointsClicked -> {
                val selectedPoints = POINTS.values()[event.index]

                val toolsList = previousState.toolsList.map {
                    if (it.type = TOOLS.POINTS) {
                        it.copy(selectedPoints = selectedPoints)
                    } else {
                        it
                    }
                }
                return previousState.copy(
                    toolsList = toolsList,
                    canvasViewState = previousState.canvasViewState.copy(points = selectedPoints)
                )
            }

            is DataEvent.OnSetDefaultTools -> {
                val toolsList = previousState.toolsList.map { model ->
                    if (model.type == event.tool) {
                        model.copy(
                            isSelected = true,
                            selectedColor = event.color,
                            selectedSize = event.size
                        )
                    } else {
                        model.copy(isSelected = false)
                    }
                }

                return previousState.copy(
                    toolsList = toolsList
                )
            }
            else -> return null
        }
    }
}