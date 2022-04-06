package io.humanoid.habittracker.domain

import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.datum.repo.RoutineRepository

class RoutineUseCases(
    private val repository: RoutineRepository
) {
    fun insertRoutine(routine: Routine) = repository.insert(routine)

    fun getRoutine(id: Long) = repository.get(id)

    fun removeRoutine(id: Long) = repository.remove(id)

    fun subscribeToAll() = repository.subscribeToAll()
}