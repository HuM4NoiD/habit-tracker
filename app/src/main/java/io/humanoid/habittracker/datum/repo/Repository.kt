package io.humanoid.habittracker.datum.repo

import androidx.lifecycle.LiveData

interface Repository<T> {

    fun get(id: Long): T?
    fun insert(data: T): Long
    fun remove(id: Long): Boolean
    fun subscribeToAll(): LiveData<List<T>>
}