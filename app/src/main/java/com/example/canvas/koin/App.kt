package com.example.canvas.koin

import android.app.Application
import com.example.canvas.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(module)
        }
    }
}