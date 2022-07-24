package io.humanoid.habittracker.ui.destinations.timer.duration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.singleton.DurationClock
import io.humanoid.habittracker.ui.component.Stopwatch
import io.humanoid.habittracker.ui.destinations.entryinput.EntryInputUiEvent
import io.humanoid.habittracker.ui.destinations.entryinput.EntryInputViewModel
import kotlinx.coroutines.delay

@Composable
@Destination
fun DurationTimerScreen(
    task: Task,
    navigator: DestinationsNavigator
) {
    val viewModel: DurationTimerViewModel = viewModel()
    val entryInput: EntryInputViewModel = viewModel(factory = EntryInputViewModel.Factory(task.id))
    var startTime by remember {
        mutableStateOf(3)
    }

    val timeElapsed = viewModel.timeElapsed.collectAsState()
    val clockState = viewModel.clockState.collectAsState()

    LaunchedEffect(key1 = startTime) {
        if (startTime > 0) {
            delay(1000L)
            startTime -= 1
        } else if (startTime == 0) {
            viewModel.onUiEvent(DurationTimerUiEvent.START)
        }
    }

    RepsTimerContent(
        task = task,
        startTime = startTime,
        timeElapsed = timeElapsed,
        clockState = clockState,
        onPause = { viewModel.onUiEvent(DurationTimerUiEvent.PAUSE) },
        onResume = { viewModel.onUiEvent(DurationTimerUiEvent.RESUME) },
        onFinish = { time ->
            entryInput.onUiEvent(
                EntryInputUiEvent.AddEntry(
                    Entry(count = (time / 10L).toInt())
                )
            )
            viewModel.onUiEvent(DurationTimerUiEvent.STOP)
            navigator.popBackStack()
        }
    )
}

@Composable
fun RepsTimerContent(
    task: Task,
    startTime: Int,
    timeElapsed: State<Long>,
    clockState: State<DurationClock.ClockState>,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onFinish: (Long) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = task.name + " id: ${task.id}",
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(32.dp)
        )
        if (startTime > 0) {
            Text(
                text = "$startTime",
                style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.primary)
            )
        } else {
            val seconds = timeElapsed.value / 1000L
            val centiSeconds = (timeElapsed.value % 1000L) / 10
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Stopwatch(seconds = seconds, centiSeconds = centiSeconds)
                if (clockState.value == DurationClock.ClockState.RUNNING || clockState.value == DurationClock.ClockState.PAUSED) {
                    FloatingActionButton(
                        onClick = {
                            when (clockState.value) {
                                DurationClock.ClockState.PAUSED -> onResume()
                                DurationClock.ClockState.RUNNING -> onPause()
                                else -> {}
                            }
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        when (clockState.value) {
                            DurationClock.ClockState.PAUSED -> Icon(
                                painter = painterResource(id = R.drawable.ic_play_right),
                                contentDescription = "Pause Timer"
                            )
                            DurationClock.ClockState.RUNNING -> Icon(
                                painter = painterResource(id = R.drawable.ic_str_pause),
                                contentDescription = "Resume Timer"
                            )
                            else -> {}
                        }
                    }
                }
                FloatingActionButton(
                    onClick = {
                        onFinish(timeElapsed.value)
                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_str_check), contentDescription = "Record Time")
                }
            }
        }
    }
}