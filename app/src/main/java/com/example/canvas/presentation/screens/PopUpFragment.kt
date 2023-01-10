package com.example.canvas.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.canvas.R


class PopUpFragment : DialogFragment() {

    private val etSetColor: EditText by lazy { requireActivity().findViewById(R.id.etSetColor) }
    private val btnPreview: Button by lazy { requireActivity().findViewById(R.id.btnPreview) }
    private val btnSetBackgroundFill: Button by lazy {
        requireActivity().findViewById(R.id.btnSetBackgroundFill)
    }
    private val popupLayout: ConstraintLayout by lazy {
        requireActivity().findViewById(R.id.popupLayout)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pop_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO -> set background color for popupFragment
        //TODO -> set background color for DrawView

    }

}