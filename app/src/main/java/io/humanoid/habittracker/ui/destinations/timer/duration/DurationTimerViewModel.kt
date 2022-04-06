package io.humanoid.habittracker.ui.destinations.timer.duration

import io.humanoid.habittracker.datum.singleton.DurationClock
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

class DurationTimerViewModel: BaseViewModel<DurationTimerUiEvent>() {

    val timeElapsed = DurationClock.timeElapsed
    val clockState = DurationClock.clockState

    override fun onUiEvent(event: DurationTimerUiEvent) {
        when (event) {
            DurationTimerUiEvent.START -> DurationClock.start()
            DurationTimerUiEvent.STOP -> DurationClock.stop()
            DurationTimerUiEvent.PAUSE -> DurationClock.pause()
            DurationTimerUiEvent.RESUME -> DurationClock.resume()
        }
    }
}