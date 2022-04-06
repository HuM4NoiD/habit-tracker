package io.humanoid.habittracker.ui.destinations.task.list

import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class TaskListUiEvent: BaseViewModel.UiEvent {
    class Insert(val task: Task): TaskListUiEvent()
    class Delete(val id: Long): TaskListUiEvent()
}
