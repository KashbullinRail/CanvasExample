package com.example.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import com.example.canvas.feature.model.CanvasViewModel
import com.example.canvas.mainscreen.DrawView
import com.example.canvas.mainscreen.ToolsLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PALETTE_VIEW = 0
        private const val TOOLS_VIEW = 1
        private const val SIZE_VIEW = 1
    }
    private val viewModel: CanvasViewModel by viewModel()


    private var toolsList: List<ToolsLayout> = listOf()

    private val paletteLayout: ToolsLayout by lazy { findViewById(R.id.paletteLayout) }
    private val toolsLayout: ToolsLayout by lazy { findViewById(R.id.toolLayout) }
    private val sizeLayout: ToolsLayout by lazy { findViewById(R.id.sizeLayout) }
    private val ivTools: ImageView by lazy { findViewById(R.id.ivTools) }
    private val drawView: DrawView by lazy { findViewById(R.id.viewDraw) }
    private val ivClear: ImageView by lazy { findViewById(R.id.ivClear) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolsList = listOf(paletteLayout, toolsLayout)
        viewModel.viewState.observe(this, ::render)

        paletteLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnPaletteClicked(it))
        }

        toolsLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolsClick(it))
        }

        ivTools.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolbarClicked)
        }

        sizeLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnSizeClick(it))
        }

        ivClear.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolbarClicked)
            drawView.clear()
        }

    }

    private fun render(viewState: ViewState) {

        with(toolsList[PALETTE_VIEW]) {
            render(viewState.colorList)
            isVisible = viewState.isPaletteVisible
        }

        with(toolsList[SIZE_VIEW]) {
            render(viewState.sizeList)
            isVisible = viewState.isBrushSizeChangerVisible
        }

        with(toolsList[TOOLS_VIEW]) {
            render(viewState.toolsList)
            isVisible = viewState.isToolsVisible
        }


        drawView.render(viewState.canvasViewState)
    }
}