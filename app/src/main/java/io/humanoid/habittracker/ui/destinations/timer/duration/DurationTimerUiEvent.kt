package io.humanoid.habittracker.ui.destinations.timer.duration

import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

enum class DurationTimerUiEvent: BaseViewModel.UiEvent {
    START, STOP, PAUSE, RESUME
}
