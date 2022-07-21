package io.humanoid.habittracker.ui.destinations.routine.detail

import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class RoutineDetailUiEvent : BaseViewModel.UiEvent {
    data class TasksSelected(val taskIds: LongArray): RoutineDetailUiEvent()
}
