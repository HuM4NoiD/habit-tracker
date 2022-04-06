package io.humanoid.habittracker.ui.destinations.routine.list

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class RoutineListViewModel : BaseViewModel<RoutineListUiEvent>() {

    val routineUseCases = appModule.domainModule.routineUseCases

    val listLiveData: LiveData<List<Routine>> = routineUseCases.subscribeToAll()

    override fun onUiEvent(event: RoutineListUiEvent) {
        when (event) {
            is RoutineListUiEvent.Delete -> removeRoutine(event.id)
            is RoutineListUiEvent.Insert -> insertRoutine(event.routine)
        }
    }

    private fun insertRoutine(routine: Routine) = routineUseCases.insertRoutine(routine)

    private fun removeRoutine(id: Long) {
        routineUseCases.removeRoutine(id)
    }
}