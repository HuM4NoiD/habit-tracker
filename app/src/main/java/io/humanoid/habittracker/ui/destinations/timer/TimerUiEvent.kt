package io.humanoid.habittracker.ui.destinations.timer

import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class TimerUiEvent: BaseViewModel.UiEvent {
    data class StartRepsTimer(val timeInSeconds: Int): TimerUiEvent()
    object PauseRepsTimer     : TimerUiEvent()
    object ResumeRepsTimer    : TimerUiEvent()
    data class FinishRepsTimer(val taskId: Long, val count: Int): TimerUiEvent()
    object StartDurationTimer : TimerUiEvent()
    object PauseDurationTimer : TimerUiEvent()
    object ResumeDurationTimer: TimerUiEvent()
    data class FinishDurationTimer(val taskId: Long, val count: Int): TimerUiEvent()
    object GoNext             : TimerUiEvent()
}
