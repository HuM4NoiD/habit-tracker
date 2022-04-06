package io.humanoid.habittracker.di

import android.content.Context
import io.humanoid.habittracker.datum.database.Database

val appModule: AppModule = AppContainer

interface AppModule {
    val dataAccessModule: DataAccessModule
    val domainModule: DomainModule

    fun initialize(context: Context)
}

private object AppContainer: AppModule {

    override fun initialize(context: Context) {
        Database.init(context)
    }

    override val dataAccessModule by lazy {
        io.humanoid.habittracker.di.dataAccessModule
    }

    override val domainModule by lazy {
        io.humanoid.habittracker.di.domainModule
    }
}