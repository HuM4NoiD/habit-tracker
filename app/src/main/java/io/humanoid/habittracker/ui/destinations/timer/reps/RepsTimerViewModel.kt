package io.humanoid.habittracker.ui.destinations.timer.reps

import androidx.lifecycle.asLiveData
import io.humanoid.habittracker.datum.singleton.RepsClock
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class RepsTimerViewModel: BaseViewModel<RepsTimerUiEvent>() {

    val timer = RepsClock.timeLeftInMillis.asLiveData()
    val timerState = RepsClock.timerState.asLiveData()

    override fun onUiEvent(event: RepsTimerUiEvent) {
        when (event) {
            is RepsTimerUiEvent.StartTimer -> RepsClock.startTimer(event.timeInSeconds)
            RepsTimerUiEvent.PauseTimer -> RepsClock.pauseTimer()
            RepsTimerUiEvent.ResumeTimer -> RepsClock.resumeTimer()
            RepsTimerUiEvent.ResetTimer -> RepsClock.stopTimer()
        }
    }
}