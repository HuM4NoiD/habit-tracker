package io.humanoid.habittracker.datum.repo

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Task
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription

interface TaskRepository: Repository<Task> {

    fun get(ids: LongArray): List<Task>

    fun observeTask(observer: DataObserver<Class<Task>>): DataSubscription

    fun subscribeForRoutine(routineId: Long): LiveData<List<Task>>
}