package io.humanoid.habittracker.datum.singleton

import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object RepsClock {

    private const val TAG = "RepsClock"

    var timer: CountDownTimer? = null

    private val timeLeftInMillisMut = MutableStateFlow<Long>(0)
    val timeLeftInMillis: Flow<Long> = timeLeftInMillisMut

    private val timerStateFlow = MutableStateFlow(TimerState.NOT_RUNNING)
    val timerState: Flow<TimerState> = timerStateFlow

    fun startTimer(durationInSeconds: Int) {
        timerStateFlow.value = TimerState.RUNNING
        timer = RepsCountDownTimer(durationInSeconds * 1000L, 1000).start()
        Log.d(TAG, "startTimer: Timer Started with duration: $durationInSeconds, $timer")
    }

    fun pauseTimer() {
        Log.d(TAG, "pauseTimer: Timer Paused, remaining: ${timeLeftInMillisMut.value / 1000}")
        timerStateFlow.value = TimerState.PAUSED
        timer?.cancel()
    }

    fun resumeTimer() {
        Log.d(TAG, "resumeTimer: Timer Resumed from ${timeLeftInMillisMut.value / 1000}")
        timerStateFlow.value = TimerState.RUNNING
        timer = RepsCountDownTimer(timeLeftInMillisMut.value, 1000).start()
    }

    fun stopTimer() {
        Log.d(TAG, "stopTimer: Timer Stopped")
        timerStateFlow.value = TimerState.NOT_RUNNING
        timeLeftInMillisMut.value = 0
        timer?.cancel()
    }

    enum class TimerState {
        NOT_RUNNING, RUNNING, PAUSED, FINISHED
    }

    private class RepsCountDownTimer(
        millisInFuture: Long,
        countdownInterval: Long
    ): CountDownTimer(millisInFuture, countdownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            timeLeftInMillisMut.value = millisUntilFinished
        }

        override fun onFinish() {
            timerStateFlow.value = TimerState.FINISHED
        }
    }
}
