package com.example.canvas

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import com.example.canvas.base.Item
import com.example.canvas.mainscreen.ToolItem
import com.example.canvas.settings.SIZE
import com.example.canvas.settings.TOOLS
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ColorModel, Item>(
        R.layout.item_palette
    ) {

        val color: ImageView = findViewById(R.id.color)
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind { list ->
            color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }


@SuppressLint("UseCompatLoadingForDrawables")
fun sizeAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.SizeModel, Item>(
        R.layout.item_size
    ) {

        val size: ImageView = findViewById(R.id.size)
        itemView.setOnClickListener { onClick(adapterPosition) }

        bind { list ->
            size.setImageDrawable(context.resources.getDrawable(item.size))
        }
    }


@SuppressLint("UseCompatLoadingForDrawables")
fun toolsAdapterDelegate(
    onToolsClick: (Int) -> Unit
): AdapterDelegate<List<Item>> = adapterDelegateLayoutContainer<ToolItem.ToolModel, Item>(
    R.layout.item_tools
) {

    val ivTool: ImageView = findViewById(R.id.ivTool)

    bind { list ->
        ivTool.setImageResource(item.type.value)

//        if (itemView.tvToolsText.visibility == View.VISIBLE) {
//            itemView.tvToolsText.visibility = View.GONE
//        }

        when (item.type) {

//            TOOLS.SIZE ->
////                itemView.tvToolsText.visibility = View.VISIBLE
////                itemView.tvToolsText.text = item.selectedSize.value.toString()
//            }

            TOOLS.PALETTE -> {
                ivTool.setColorFilter(
                    context.resources.getColor(item.selectedColor.value, null),
                    PorterDuff.Mode.SRC_IN
                )
            }

            TOOLS.SIZE -> {
                ivTool.setImageDrawable(
                    context.resources.getDrawable(item.selectedSize.value)
                )
            }

            else -> {
                if (item.isSelected) {
                    ivTool.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    ivTool.setBackgroundResource(android.R.color.transparent)
                }
            }
        }

        itemView.setOnClickListener {
            onToolsClick(adapterPosition)
        }
    }
}