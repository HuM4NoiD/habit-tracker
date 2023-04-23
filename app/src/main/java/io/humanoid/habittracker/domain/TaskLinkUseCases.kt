package io.humanoid.habittracker.domain

import io.humanoid.habittracker.datum.model.TaskLink
import io.humanoid.habittracker.datum.repo.TaskLinkRepository

class TaskLinkUseCases(
    private val repository: TaskLinkRepository
) {
    
    fun insertLink(taskLink: TaskLink) = repository.insert(taskLink)

    fun getTaskLink(id: Long) = repository.get(id)

    fun removeTaskLink(id: Long) = repository.remove(id)

    fun removeTaskLinks(ids: LongArray) = repository.remove(ids)

    fun subscribeForRoutine(routineId: Long) = repository.subscribeForRoutine(routineId)
}