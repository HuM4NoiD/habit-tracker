package io.humanoid.habittracker.domain

import androidx.lifecycle.MutableLiveData
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.repo.TaskRepository
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription

class TaskUseCases(
    private val repository: TaskRepository
) {

    fun insertTask(task: Task) = repository.insert(task)

    fun observeTask(taskId: Long, taskLiveData: MutableLiveData<Task>): DataSubscription {
        val observer = buildObserver(taskId, taskLiveData)
        return repository.observeTask(observer)
    }

    fun getTask(id: Long) = repository.get(id)

    fun getTasks(ids: LongArray) = repository.get(ids)

    fun removeTask(id: Long) = repository.remove(id)

    fun subscribeToAll() = repository.subscribeToAll()

    private fun buildObserver(taskId: Long, taskLiveData: MutableLiveData<Task>) = DataObserver<Class<Task>> { _ ->
        val newTask = repository.get(taskId)
        taskLiveData.postValue(newTask)
    }
}