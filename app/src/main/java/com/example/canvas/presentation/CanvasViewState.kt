package com.example.canvas.presentation

import com.example.canvas.data.settings.COLOR
import com.example.canvas.data.settings.POINTS
import com.example.canvas.data.settings.SIZE
import com.example.canvas.data.settings.TOOLS


data class CanvasViewState(val color: COLOR, val size: SIZE, val points: POINTS, val tools: TOOLS)

