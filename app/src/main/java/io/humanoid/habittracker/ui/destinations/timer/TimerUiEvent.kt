package io.humanoid.habittracker.ui.destinations.timer

import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class TimerUiEvent: BaseViewModel.UiEvent {
    data class StartRepsTimer(val timeInSeconds: Int): TimerUiEvent()
    object PauseRepsTimer     : TimerUiEvent()
    object ResumeRepsTimer    : TimerUiEvent()
    object FinishRepsTimer    : TimerUiEvent()
    object StartDurationTimer : TimerUiEvent()
    object PauseDurationTimer : TimerUiEvent()
    object ResumeDurationTimer: TimerUiEvent()
    object FinishDurationTimer: TimerUiEvent()
    object GoNext             : TimerUiEvent()
}
