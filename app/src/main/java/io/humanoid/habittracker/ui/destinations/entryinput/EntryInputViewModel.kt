package io.humanoid.habittracker.ui.destinations.entryinput

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class EntryInputViewModel(
    private val taskId: Long
): BaseViewModel<EntryInputUiEvent>() {
    private val taskUseCases = appModule.domainModule.taskUseCases

    private val task = taskUseCases.getTask(taskId)!!
    private val averageEntryCount: Int = task.entries.map { entry -> entry.count }.average().toInt()
    val entryState = mutableStateOf(averageEntryCount)

    override fun onUiEvent(event: EntryInputUiEvent) {
        when (event) {
            is EntryInputUiEvent.AddEntry -> addEntry(event.entry)
        }
    }

    private fun addEntry(entry: Entry) {
        task.entries.reset()
        task.entries.add(entry)
        taskUseCases.insertTask(task)
    }

    class Factory(val taskId: Long): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EntryInputViewModel(taskId) as T
        }
    }

    companion object {
        private const val TAG = "EntryInputViewModel"
    }
}