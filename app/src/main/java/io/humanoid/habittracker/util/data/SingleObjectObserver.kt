package io.humanoid.habittracker.util.data

import io.humanoid.habittracker.datum.database.Database
import io.objectbox.kotlin.query
import io.objectbox.reactive.DataObserver

class SingleObjectObserver<T>(
    val id: Long
): DataObserver<Class<T>> {

    override fun onData(data: Class<T>) {
        val entity = Database.store.boxFor(data).query {  }
    }
}