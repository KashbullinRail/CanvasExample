package com.example.canvas

import com.example.canvas.presentation.CanvasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val module = module {
    viewModel {
        CanvasViewModel()
    }
}