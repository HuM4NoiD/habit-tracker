package io.humanoid.habittracker.ui.destinations.task.detail

import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class TaskDetailUiEvent: BaseViewModel.UiEvent {
    data class UpdateTask(val task: Task): TaskDetailUiEvent()
    data class AddEntry(val entry: Entry): TaskDetailUiEvent()
}
