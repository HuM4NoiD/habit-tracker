package io.humanoid.habittracker.ui.destinations.task.list

import androidx.lifecycle.LiveData
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.domain.TaskUseCases
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class TaskListViewModel: BaseViewModel<TaskListUiEvent>() {

    val taskUseCases: TaskUseCases = appModule.domainModule.taskUseCases

    val listLiveData: LiveData<List<Task>> = taskUseCases.subscribeToAll()

    override fun onUiEvent(event: TaskListUiEvent) {
        when (event) {
            is TaskListUiEvent.Delete -> {
                removeTask(event.id)
            }
            is TaskListUiEvent.Insert -> {
                insertTask(event.task)
            }
        }
    }

    private fun insertTask(task: Task) {
        taskUseCases.insertTask(task)
    }

    private fun removeTask(id: Long) {
        taskUseCases.removeTask(id)
    }
}