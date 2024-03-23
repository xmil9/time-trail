package net.mikelindner.timetrail.app

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        TrailsService.provide(this)
    }
}