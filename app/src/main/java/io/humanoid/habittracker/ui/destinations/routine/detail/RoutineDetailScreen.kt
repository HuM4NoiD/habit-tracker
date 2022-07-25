package io.humanoid.habittracker.ui.destinations.routine.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import io.humanoid.habittracker.R
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.ui.component.TaskListItem
import io.humanoid.habittracker.ui.destinations.destinations.RoutineInputSheetDestination
import io.humanoid.habittracker.ui.destinations.destinations.TaskSelectionSheetDestination
import io.humanoid.habittracker.ui.destinations.destinations.TimerScreenDestination
import kotlinx.coroutines.launch

@Composable
@Destination
fun RoutineDetailScreen(
    routineId: Long,
    navigator: DestinationsNavigator,
    selectionRecipient: ResultRecipient<TaskSelectionSheetDestination, LongArray>,
    modifier: Modifier = Modifier
) {
    val viewModel: RoutineDetailViewModel = viewModel(factory = RoutineDetailViewModel.Factory(routineId))
    val routineState = viewModel.routineLiveData.observeAsState()

    if (routineState.value == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.ic_str_alert),
                    contentDescription = "Alert",
                    modifier = Modifier.size(100.dp)
                )
                Text(text = "It appears that this Routine was deleted or does not exist!")
            }
        }
    }
    routineState.value?.let { routine ->
        RoutineDetailContent(routine, viewModel, navigator, selectionRecipient, modifier)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RoutineDetailContent(
    routine: Routine,
    viewModel: RoutineDetailViewModel,
    navigator: DestinationsNavigator,
    selectionRecipient: ResultRecipient<TaskSelectionSheetDestination, LongArray>,
    modifier: Modifier = Modifier
) {
    selectionRecipient.onResult {
        viewModel.onUiEvent(RoutineDetailUiEvent.TasksSelected(it))
    }

    val tasksState = viewModel.selectedTasksLiveData.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (tasksState.value.isNullOrEmpty()) {
                        coroutineScope.launch {
                            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                "There are no tasks in this routine!",
                                "Add Task"
                            )
                            when (snackbarResult) {
                                SnackbarResult.ActionPerformed -> navigator.navigate(
                                    TaskSelectionSheetDestination(
                                        tasksState.value?.map { task -> task.id }?.toLongArray() ?: LongArray(0)
                                    )
                                )
                                else -> {}
                            }
                        }
                    } else {
                        navigator.navigate(
                            TimerScreenDestination(
                                interval = routine.interval,
                                taskIds = tasksState.value?.map { task -> task.id }?.toLongArray() ?: LongArray(0)
                            )
                        )
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_str_launch), contentDescription = "Add entry")
            }
        }
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = routine.name,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 32.dp)
                    )
                    IconButton(
                        onClick = {
                            navigator.navigate(RoutineInputSheetDestination(routine))
                        },
                        modifier = Modifier
                            .padding(32.dp)
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_str_edit),
                            contentDescription = "Edit this Routine",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 32.dp, end = 32.dp, top = 32.dp)
                        ) {
                            Text(
                                text = "Tasks In this Routine",
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    navigator.navigate(
                                        TaskSelectionSheetDestination(
                                            tasksState.value?.map { task -> task.id }?.toLongArray() ?: LongArray(0)
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .size(48.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_str_edit),
                                    contentDescription = "Edit this Routine",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        if (tasksState.value.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = "There are no tasks for this routine",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                            ) {
                                items(items = tasksState.value!!) { task ->
                                    TaskListItem(task = task)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}