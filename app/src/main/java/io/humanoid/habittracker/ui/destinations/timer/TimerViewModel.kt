package io.humanoid.habittracker.ui.destinations.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class TimerViewModel(
    val id: Long,
    val isTask: Boolean
): BaseViewModel<TimerUiEvent>() {

    val taskLiveData = MutableLiveData<Task>()
    val routineLiveData = MutableLiveData<Routine>()

    override fun onUiEvent(event: TimerUiEvent) {
        when (event) {
        }
    }

    class Factory(
        val id: Long,
        val isTask: Boolean
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TimerViewModel(id, isTask) as T
        }
    }
}