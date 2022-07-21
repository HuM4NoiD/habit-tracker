package io.humanoid.habittracker.domain

import androidx.lifecycle.MutableLiveData
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.datum.repo.RoutineRepository
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription

class RoutineUseCases(
    private val repository: RoutineRepository
) {
    fun insertRoutine(routine: Routine) = repository.insert(routine)

    fun getRoutine(id: Long) = repository.get(id)

    fun observeRoutine(routineId: Long, routineLiveData: MutableLiveData<Routine>): DataSubscription {
        return repository.observeRoutine(buildObserver(routineId, routineLiveData))
    }

    fun removeRoutine(id: Long) = repository.remove(id)

    fun subscribeToAll() = repository.subscribeToAll()

    private fun buildObserver(routineId: Long, routineLiveData: MutableLiveData<Routine>) = DataObserver<Class<Routine>> {
        val newRoutine = repository.get(routineId)
        routineLiveData.postValue(newRoutine)
    }
}