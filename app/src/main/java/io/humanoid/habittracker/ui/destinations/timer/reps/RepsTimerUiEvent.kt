package io.humanoid.habittracker.ui.destinations.timer.reps

import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class RepsTimerUiEvent: BaseViewModel.UiEvent {
    data class StartTimer(val timeInSeconds: Int): RepsTimerUiEvent()
    object PauseTimer: RepsTimerUiEvent()
    object ResumeTimer: RepsTimerUiEvent()
    object ResetTimer: RepsTimerUiEvent()
}
