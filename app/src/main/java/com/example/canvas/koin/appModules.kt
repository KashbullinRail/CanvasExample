package com.example.canvas

import com.example.canvas.feature.model.CanvasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val module = module {
    viewModel {
        CanvasViewModel()
    }
}