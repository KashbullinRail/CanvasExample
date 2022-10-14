package com.example.canvas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun RecyclerView.setAdapterAndCleanupOnDetachFromWindow(recyclerViewAdapter: RecyclerView.Adapter<*>) {
    adapter = recyclerViewAdapter
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            adapter = null
            removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) {
        }
    })
}