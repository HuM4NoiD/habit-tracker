package io.humanoid.habittracker.ui.destinations.timer.reps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.humanoid.habittracker.R
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.singleton.RepsClock
import io.humanoid.habittracker.ui.component.CountDownRing
import io.humanoid.habittracker.ui.destinations.destinations.RepsEntryInputSheetDestination
import kotlinx.coroutines.delay

@Composable
@Destination
fun RepsTimerScreen(
    navigator: DestinationsNavigator,
    task: Task
) {
    val viewModel: RepsTimerViewModel = viewModel()
    var startTime by remember {
        mutableStateOf(3)
    }

    val timer = viewModel.timer.observeAsState()
    val timerState = viewModel.timerState.observeAsState()

    LaunchedEffect(key1 = startTime) {
        if (startTime > 0) {
            delay(1000L)
            startTime -= 1
        } else if (startTime == 0) {
            viewModel.onUiEvent(RepsTimerUiEvent.StartTimer(20))
        }
    }

    LaunchedEffect(key1 = timerState.value) {
        if (timerState.value == RepsClock.TimerState.FINISHED) {
            viewModel.onUiEvent(RepsTimerUiEvent.ResetTimer)
            navigator.navigate(RepsEntryInputSheetDestination(task.id, true))
        }
    }

    RepsTimerContent(
        task = task,
        startTime = startTime,
        totalTimeInSeconds = 20,
        timer = timer,
        timerState = timerState,
        onPause = { viewModel.onUiEvent(RepsTimerUiEvent.PauseTimer) },
        onResume = { viewModel.onUiEvent(RepsTimerUiEvent.ResumeTimer) }
    )
}

@Composable
fun RepsTimerContent(
    task: Task,
    startTime: Int,
    totalTimeInSeconds: Int,
    timer: State<Long?>,
    timerState: State<RepsClock.TimerState?>,
    onPause: () -> Unit,
    onResume: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            if (startTime > 0) {
                Text(
                    text = "$startTime",
                    style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.primary)
                )
            } else {
                val actualTime = ((timer.value ?: 0) / 1000f).toInt()
                CountDownRing(
                    progress = actualTime / totalTimeInSeconds.toFloat(),
                    seconds = actualTime
                )
                if (timerState.value != RepsClock.TimerState.NOT_RUNNING && timerState.value != RepsClock.TimerState.FINISHED) {
                    FloatingActionButton(
                        onClick = {
                            when (timerState.value) {
                                RepsClock.TimerState.PAUSED -> onResume()
                                RepsClock.TimerState.RUNNING -> onPause()
                                else -> {}
                            }
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        when (timerState.value) {
                            RepsClock.TimerState.PAUSED -> Icon(
                                painter = painterResource(id = R.drawable.ic_play_right),
                                contentDescription = "Pause Timer"
                            )
                            RepsClock.TimerState.RUNNING -> Icon(
                                painter = painterResource(id = R.drawable.ic_str_pause),
                                contentDescription = "Resume Timer"
                            )
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}