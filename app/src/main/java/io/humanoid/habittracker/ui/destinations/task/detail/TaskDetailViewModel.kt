package io.humanoid.habittracker.ui.destinations.task.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class TaskDetailViewModel(
    taskId: Long
) : BaseViewModel<TaskDetailUiEvent>() {

    val taskUseCases = appModule.domainModule.taskUseCases
    val entryUseCases = appModule.domainModule.entryUseCases

    val taskState = mutableStateOf(taskUseCases.getTask(taskId)!!)
    val taskLiveData = MutableLiveData(taskUseCases.getTask(taskId)!!)
    val taskSubscription = taskUseCases.observeTask(taskId, taskLiveData)

    val entriesLiveData = entryUseCases.subscribeForTask(taskState.value.id)

    override fun onUiEvent(event: TaskDetailUiEvent) {
        when (event) {
            is TaskDetailUiEvent.UpdateTask -> updateTask(event.task)
            is TaskDetailUiEvent.AddEntry -> addEntry(event.entry)
        }
    }

    private fun updateTask(task: Task) {
        taskUseCases.insertTask(task)
        taskState.value = task
    }

    private fun addEntry(entry: Entry) {
        taskState.value.entries.add(entry)
        val id = taskUseCases.insertTask(taskState.value)
        taskState.value = taskUseCases.getTask(id)!!
    }

    override fun onCleared() {
        super.onCleared()
        taskSubscription.cancel()
    }

    class Factory(val taskId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskDetailViewModel(taskId) as T
        }
    }

    companion object {
        private const val TAG = "TaskDetailViewModel"
    }
}