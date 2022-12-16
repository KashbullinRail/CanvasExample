package com.example.canvas

import com.example.canvas.data.model.CanvasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val module = module {
    viewModel {
        CanvasViewModel()
    }
}