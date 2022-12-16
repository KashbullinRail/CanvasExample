package com.example.canvas.presentation

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.canvas.R
import com.example.canvas.UiEvent
import com.example.canvas.ViewState
import com.example.canvas.data.model.CanvasViewModel
import com.example.canvas.domain.DrawView
import com.example.canvas.domain.ToolsLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    companion object {
        private const val PALETTE_VIEW = 0
        private const val TOOLS_VIEW = 1
        private const val SIZE_VIEW = 2
    }

    private val viewModel: CanvasViewModel by viewModel()
    private var toolsList: List<ToolsLayout> = listOf()

    private val paletteLayout: ToolsLayout by lazy { findViewById(R.id.paletteLayout) }
    private val toolsLayout: ToolsLayout by lazy { findViewById(R.id.toolLayout) }
    private val ivTools: ImageView by lazy { findViewById(R.id.ivTools) }
    private val drawView: DrawView by lazy { findViewById(R.id.viewDraw) }
    private val ivClear: ImageView by lazy { findViewById(R.id.ivClear) }
    private val tvSizeLayout: ToolsLayout by lazy { findViewById(R.id.tvSizeLayout) }
    private val ivSetBackgroun: ImageView by lazy { findViewById(R.id.ivSetBackground) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolsList = listOf(paletteLayout, toolsLayout, tvSizeLayout)

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

        tvSizeLayout.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnSizeClicked(it))
        }

        ivClear.setOnClickListener {
            drawView.clear()
        }

        popupMenu()

//            val showPopUp = PopUpFragment()
//            showPopUp.show(supportFragmentManager, "showPopUp")


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

    private fun popupMenu() {

        val popupMenu = PopupMenu(this, ivSetBackgroun)
        popupMenu.inflate(R.menu.item_menu)

        popupMenu.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.itemM_ImageStorage -> {
                    Toast.makeText(this, "${it.title}", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemM_camera -> {
                    Toast.makeText(this, "${it.title}", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemM_searchPicture -> {
                    Toast.makeText(this, "${it.title}", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemM_setBackgroundFill -> {
                    Toast.makeText(this, "${it.title}", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }

        }

        ivSetBackgroun.setOnClickListener {

            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
            true

        }

    }

}