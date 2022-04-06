package io.humanoid.habittracker.ui.destinations.routine.list

import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class RoutineListUiEvent: BaseViewModel.UiEvent {
    class Insert(val routine: Routine): RoutineListUiEvent()
    class Delete(val id: Long): RoutineListUiEvent()
}