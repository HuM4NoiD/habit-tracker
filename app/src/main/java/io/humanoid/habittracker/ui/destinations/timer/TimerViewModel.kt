package io.humanoid.habittracker.ui.destinations.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.singleton.DurationClock
import io.humanoid.habittracker.datum.singleton.RepsClock
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class TimerViewModel(
    val taskIds: LongArray
): BaseViewModel<TimerUiEvent>() {

    private val taskUseCases = appModule.domainModule.taskUseCases

    private var currentIndex = 0;

    private val taskList = taskUseCases.getTasks(taskIds)
    private val _screenState = mutableStateOf<TimerScreenState>(TimerScreenState.Interstitial(taskList[currentIndex].name))
    val screenState: State<TimerScreenState> = _screenState

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
            TimerUiEvent.FinishRepsTimer -> finishRepsTimer()

            TimerUiEvent.StartDurationTimer -> startDurationTimer()
            TimerUiEvent.PauseDurationTimer -> pauseDurationTimer()
            TimerUiEvent.ResumeDurationTimer -> resumeDurationTimer()
            TimerUiEvent.FinishDurationTimer -> finishDurationTimer()
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
                ++ currentIndex;
                if (currentIndex < taskList.size) {
                    _screenState.value = TimerScreenState.Interstitial(taskList[currentIndex].name)
                } else {
                    _screenState.value = TimerScreenState.End
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

    private fun finishRepsTimer() {
        RepsClock.stopTimer()
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

    private fun finishDurationTimer() {
        DurationClock.stop()
    }

    class Factory(
        val taskIds: LongArray
    ): ViewModelProvider.Factory {
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
        data class Interstitial(val nextName: String): TimerScreenState()
        data class RunningTask(val task: Task): TimerScreenState()
        object End: TimerScreenState()
    }
}