package io.humanoid.habittracker.datum.database

import android.content.Context
import android.util.Log
import io.humanoid.habittracker.BuildConfig
import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.model.MyObjectBox
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.datum.model.Task
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

object Database {
    private const val TAG = "Database"

    lateinit var store: BoxStore

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context)
            .build()

        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(store).start(context)
            Log.d(
                TAG,
                "init: initialized object box store: ${BoxStore.getVersion()}, ${BoxStore.getVersionNative()}"
            )
        }
    }

    val taskBox: Box<Task> by lazy {
        store.boxFor(Task::class.java)
    }

    val routineBox: Box<Routine> by lazy {
        store.boxFor(Routine::class.java)
    }

    val entryBox: Box<Entry> by lazy {
        store.boxFor(Entry::class.java)
    }
}