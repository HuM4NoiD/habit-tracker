package io.humanoid.habittracker.ui.destinations.timer

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import io.humanoid.habittracker.datum.model.TaskType
import io.humanoid.habittracker.datum.singleton.RepsClock
import io.humanoid.habittracker.ui.destinations.destinations.RepsEntryInputSheetDestination
import io.humanoid.habittracker.ui.destinations.timer.duration.DurationTimerContent
import io.humanoid.habittracker.ui.destinations.timer.reps.RepsTimerContent
import io.humanoid.habittracker.ui.util.IdCount
import kotlinx.coroutines.delay

@Composable
@Destination
fun TimerScreen(
    navigator: DestinationsNavigator,
    repsResultRecipient: ResultRecipient<RepsEntryInputSheetDestination, IdCount>,
    taskIds: LongArray
) {
    val viewModel = viewModel<TimerViewModel>(factory = TimerViewModel.Factory(taskIds))

    var interstitialTime by remember {
        mutableStateOf(5)
    }
    val context = LocalContext.current

    val screenStateObservable = viewModel.screenState
    val screenState = screenStateObservable.value

    val repsTimer = viewModel.repsTimer.observeAsState()
    val repsTimerState = viewModel.repsTimerState.observeAsState()

    val durationTimer = viewModel.durationTimer.collectAsState()
    val durationTimerState = viewModel.durationTimerState.collectAsState()

    LaunchedEffect(key1 = interstitialTime) {
        if (interstitialTime > 0) {
            delay(1000)
            interstitialTime -= 1
        } else if (interstitialTime == 0) {
            Toast.makeText(context, "Interstitial Over!", Toast.LENGTH_SHORT).show()
            viewModel.onUiEvent(TimerUiEvent.GoNext)
        }
    }

    repsResultRecipient.onResult { pair ->
        viewModel.onUiEvent(TimerUiEvent.FinishRepsTimer(pair.id, pair.count))
        viewModel.onUiEvent(TimerUiEvent.GoNext)
        interstitialTime = 5
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (screenState) {
            is TimerViewModel.TimerScreenState.Interstitial -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(32.dp)
                ) {
                    Text(
                        text = "Up Next",
                        style = MaterialTheme.typography.h4
                    )
                    Text(
                        text = screenState.nextName,
                        style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.primary)
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = interstitialTime.toString(),
                        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }
            is TimerViewModel.TimerScreenState.RunningTask -> {
                val task = screenState.task
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(32.dp)
                ) {
                    Text(
                        text = task.name,
                        style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.primary)
                    )
                }

                val timerModifier = Modifier
                    .padding(top = 0.dp, start = 32.dp, end = 32.dp, bottom = 32.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            listOf(MaterialTheme.colors.primary, MaterialTheme.colors.secondary)
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )

                Card(
                    shape = RoundedCornerShape(32.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        brush = Brush.linearGradient(listOf(
                            MaterialTheme.colors.primary, MaterialTheme.colors.secondary
                        ))
                    ),
                    modifier = Modifier.padding(top = 0.dp, start = 32.dp, end = 32.dp, bottom = 32.dp)
                ) {
                    when (task.type) {
                        TaskType.REPS -> {
                            RepsTimerLaunchEffect(viewModel = viewModel, timeInSeconds = 10)

                            RepsTimerFinishEffect(
                                viewModel = viewModel,
                                taskId = task.id,
                                repsTimerState = repsTimerState,
                                navigator = navigator
                            )

                            RepsTimerContent(
                                task = task,
                                startTime = 0,
                                totalTimeInSeconds = 10,
                                timer = repsTimer,
                                timerState = repsTimerState,
                                onPause = {
                                    viewModel.onUiEvent(TimerUiEvent.PauseRepsTimer)
                                },
                                onResume = {
                                    viewModel.onUiEvent(TimerUiEvent.ResumeRepsTimer)
                                },
//                                modifier = timerModifier
                            )
                        }
                        TaskType.DURATION -> {
                            
                            DurationTimerLaunchEffect(viewModel = viewModel)
                            
                            DurationTimerContent(
                                task = task,
                                startTime = 0,
                                timeElapsed = durationTimer,
                                clockState = durationTimerState,
                                onPause = {
                                    viewModel.onUiEvent(TimerUiEvent.PauseDurationTimer)
                                },
                                onResume = {
                                    viewModel.onUiEvent(TimerUiEvent.ResumeDurationTimer)
                                },
                                onFinish = { time ->
                                    Toast.makeText(
                                        context,
                                        "Duration Timer Finished for ${task.name} with $time count",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewModel.onUiEvent(TimerUiEvent.FinishDurationTimer(task.id, time.toInt()))
                                    viewModel.onUiEvent(TimerUiEvent.GoNext)
                                    interstitialTime = 5
                                },
//                                modifier = timerModifier
                            )
                        }
                    }
                }
            }
            TimerViewModel.TimerScreenState.End -> {
                Text(text = "Done!!!", style = MaterialTheme.typography.h1)
            }
        }
    }
}

@Composable
private fun DurationTimerLaunchEffect(viewModel: TimerViewModel) {
    LaunchedEffect(true) {
        viewModel.onUiEvent(TimerUiEvent.StartDurationTimer)
    }
}

@Composable
private fun RepsTimerLaunchEffect(viewModel: TimerViewModel, timeInSeconds: Int) {
    LaunchedEffect(true) {
        viewModel.onUiEvent(TimerUiEvent.StartRepsTimer(timeInSeconds))
    }
}

@Composable
private fun RepsTimerFinishEffect(
    viewModel: TimerViewModel,
    taskId: Long,
    repsTimerState: State<RepsClock.TimerState?>,
    navigator: DestinationsNavigator
) {
    LaunchedEffect(key1 = repsTimerState.value) {
        if (repsTimerState.value == RepsClock.TimerState.FINISHED) {
            navigator.navigate(RepsEntryInputSheetDestination(taskId, false))
        }
    }
}