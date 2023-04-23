package io.humanoid.habittracker.ui.destinations.routine.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.model.TaskLink
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class RoutineDetailViewModel(
    val routineId: Long
) : BaseViewModel<RoutineDetailUiEvent>() {

    private val routineUseCases = appModule.domainModule.routineUseCases
    private val taskLinkUseCases = appModule.domainModule.taskLinkUseCases
    private val taskUseCases = appModule.domainModule.taskUseCases

    val routineLiveData = MutableLiveData<Routine>(routineUseCases.getRoutine(routineId))
    val selectedTaskLinksLiveData = taskLinkUseCases.subscribeForRoutine(routineId)
    private val routineSubscription = routineUseCases.observeRoutine(routineId, routineLiveData)

    override fun onUiEvent(event: RoutineDetailUiEvent) {
        when (event) {
            is RoutineDetailUiEvent.TasksSelected -> {
                val selectedTasks = taskUseCases.getTasks(event.taskIds)
                routineLiveData.value?.let { routine ->
                    taskLinkUseCases.removeTaskLinks(routine.taskLinks.map { taskLink -> taskLink.id }
                        .toLongArray())
                    routine.taskLinks.clear()
                    routine.taskLinks.addAll(buildTaskLinks(selectedTasks))
                    routineUseCases.insertRoutine(routine)
                    routineLiveData.postValue(routineUseCases.getRoutine(routineId))
                }
            }

            else -> {}
        }
    }

    private fun buildTaskLinks(tasks: List<Task>): List<TaskLink> {
        val links = mutableListOf<TaskLink>()
        for (i in tasks.withIndex()) {
            val link = TaskLink(index = i.index)
            link.link.target = i.value
            links.add(link)
        }
        return links
    }

    override fun onCleared() {
        super.onCleared()
        routineSubscription.cancel()
    }

    class Factory(val routineId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RoutineDetailViewModel(routineId) as T
        }
    }
}