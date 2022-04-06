package io.humanoid.habittracker.domain

import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.repo.TaskRepository

class TaskUseCases(
    private val repository: TaskRepository
) {

    fun insertTask(task: Task) = repository.insert(task)

    fun getTask(id: Long) = repository.get(id)

    fun removeTask(id: Long) = repository.remove(id)

    fun subscribeToAll() = repository.subscribeToAll()
}