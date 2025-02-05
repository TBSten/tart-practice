package me.tbsten.prac.tart.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.tbsten.prac.tart.common.initLogger

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }
}
