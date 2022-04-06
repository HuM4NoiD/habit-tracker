package io.humanoid.habittracker

import android.app.Application
import io.humanoid.habittracker.di.appModule

class HabitTracker : Application() {

    override fun onCreate() {
        super.onCreate()
        appModule.initialize(applicationContext)
    }
}