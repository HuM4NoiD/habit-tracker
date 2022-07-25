package io.humanoid.habittracker.ui.destinations.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import io.humanoid.habittracker.datum.dto.TaskDoneSummaryItem
import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.singleton.DurationClock
import io.humanoid.habittracker.datum.singleton.RepsClock
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class TimerViewModel(
    val taskIds: LongArray
) : BaseViewModel<TimerUiEvent>() {

    private val taskUseCases = appModule.domainModule.taskUseCases

    private var currentIndex = 0;

    private val taskList = taskUseCases.getTasks(taskIds)
    private val _screenState =
        mutableStateOf<TimerScreenState>(TimerScreenState.Interstitial(taskList[currentIndex].name))
    val screenState: State<TimerScreenState> = _screenState

    private val summary: ArrayList<TaskDoneSummaryItem> = ArrayList(taskIds.size)

    val repsTimer = RepsClock.timeLeftInMillis.asLiveData()
    val repsTimerState = RepsClock.timerState.asLiveData()

    val durationTimer = DurationClock.timeElapsed
    val durationTimerState = DurationClock.clockState

    override fun onUiEvent(event: TimerUiEvent) {
        when (event) {
            TimerUiEvent.GoNext -> goNext()

            is TimerUiEvent.StartRepsTimer -> startRepsTimer(event.timeInSeconds)
            TimerUiEvent.PauseRepsTimer -> pauseRepsTimer()
            TimerUiEvent.ResumeRepsTimer -> resumeRepsTimer()
            is TimerUiEvent.FinishRepsTimer -> finishRepsTimer(event.taskId, event.count)

            TimerUiEvent.StartDurationTimer -> startDurationTimer()
            TimerUiEvent.PauseDurationTimer -> pauseDurationTimer()
            TimerUiEvent.ResumeDurationTimer -> resumeDurationTimer()
            is TimerUiEvent.FinishDurationTimer -> finishDurationTimer(event.taskId, event.count)
        }
    }

    private fun goNext() {
        when (screenState.value) {
            is TimerScreenState.Interstitial -> {
                // Go to next task
                _screenState.value = TimerScreenState.RunningTask(taskList[currentIndex])
            }
            is TimerScreenState.RunningTask -> {
                // Go to interstitial
                ++currentIndex;
                if (currentIndex < taskList.size) {
                    _screenState.value = TimerScreenState.Interstitial(taskList[currentIndex].name)
                } else {
                    _screenState.value = TimerScreenState.End(summary)
                }
            }
            else -> {

            }
        }
    }

    private fun startRepsTimer(timeInSeconds: Int) {
        RepsClock.startTimer(timeInSeconds)
    }

    private fun pauseRepsTimer() {
        RepsClock.pauseTimer()
    }

    private fun resumeRepsTimer() {
        RepsClock.resumeTimer()
    }

    private fun finishRepsTimer(taskId: Long, count: Int) {
        RepsClock.stopTimer()
        addEntry(taskId, count)
    }

    private fun startDurationTimer() {
        DurationClock.start()
    }

    private fun pauseDurationTimer() {
        DurationClock.pause()
    }

    private fun resumeDurationTimer() {
        DurationClock.resume()
    }

    private fun finishDurationTimer(taskId: Long, count: Int) {
        DurationClock.stop()
        addEntry(taskId, count)
    }

    private fun addEntry(taskId: Long, count: Int) {
        val task = taskUseCases.getTask(taskId)
        task?.let {
            task.entries.add(Entry(count = count))
            task.entries.applyChangesToDb()
            summary.add(
                TaskDoneSummaryItem(
                    taskName = task.name,
                    taskType = task.type,
                    count = count
                )
            )
        }
    }

    class Factory(
        val taskIds: LongArray
    ) : ViewModelProvider.Factory {
        init {
            if (taskIds.isEmpty()) {
                throw IllegalArgumentException("taskIds is empty!")
            }
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TimerViewModel(taskIds) as T
        }
    }

    sealed class TimerScreenState {
        data class Interstitial(val nextName: String) : TimerScreenState()
        data class RunningTask(val task: Task) : TimerScreenState()
        data class End(val summary: List<TaskDoneSummaryItem>) : TimerScreenState()
    }
}