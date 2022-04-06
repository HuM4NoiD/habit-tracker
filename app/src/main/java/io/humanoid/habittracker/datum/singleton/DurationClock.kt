package io.humanoid.habittracker.datum.singleton

import android.os.SystemClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object DurationClock {

    private const val TAG = "DurationClock"

    private val timeElapsedMut = MutableStateFlow<Long>(0)
    val timeElapsed: StateFlow<Long> = timeElapsedMut

    private val clockStateMut = MutableStateFlow(ClockState.NOT_RUNNING)
    val clockState: StateFlow<ClockState> = clockStateMut

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var startTime = -1L

    private var job: Job? = null

    fun start() {
        startTime = SystemClock.elapsedRealtime()
        clockStateMut.value = ClockState.RUNNING
        startJob()
    }

    fun pause() {
        clockStateMut.value = ClockState.PAUSED
        cancelAndClearJob()
    }

    fun resume() {
        startTime = SystemClock.elapsedRealtime() - timeElapsedMut.value
        clockStateMut.value = ClockState.RUNNING
        startJob()
    }

    fun stop() {
        clockStateMut.value = ClockState.FINISHED
        startTime = -1L
        cancelAndClearJob()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                timeElapsedMut.value = SystemClock.elapsedRealtime() - startTime
                delay(10)
            }
        }
    }

    private fun cancelAndClearJob() {
        scope.coroutineContext.cancelChildren()
        job?.cancel()
        job = null
    }

    enum class ClockState {
        NOT_RUNNING, RUNNING, PAUSED, FINISHED
    }
}