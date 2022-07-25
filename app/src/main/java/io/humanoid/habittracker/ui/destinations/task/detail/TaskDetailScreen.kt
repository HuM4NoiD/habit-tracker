package io.humanoid.habittracker.ui.destinations.task.detail

import androidx.compose.foundation.border
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.humanoid.habittracker.R
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.ui.destinations.destinations.TaskInputSheetDestination
import io.humanoid.habittracker.ui.destinations.destinations.TimerScreenDestination

@Composable
@Destination
fun TaskDetailScreen(
    taskId: Long,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    val viewModel: TaskDetailViewModel = viewModel(factory = TaskDetailViewModel.Factory(taskId))
    val taskState = viewModel.taskLiveData.observeAsState()

    if (taskState.value == null) {
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
                Text(text = "It appears that this task was deleted or does not exist!")
            }
        }
    }
    taskState.value?.let { task ->
        TaskDetailContent(
            task, viewModel, navigator, modifier
        )
    }
}

@Composable
private fun TaskDetailContent(
    task: Task,
    viewModel: TaskDetailViewModel,
    navigator: DestinationsNavigator,
    modifier: Modifier
) {
    val entriesState = viewModel.entriesLiveData.observeAsState()

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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = task.name,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 4.dp)
                    )
                    Text(
                        text = task.desc,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .padding(start = 32.dp, end = 32.dp, bottom = 32.dp, top = 0.dp)
                    )
                }
                IconButton(
                    onClick = {
                        navigator.navigate(TaskInputSheetDestination(task))
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(32.dp)
                ) {
                    entriesState.value?.let { entries ->
                        items(items = entries) { entry ->
                            Text(
                                text = "Entry: ${entry.count}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colors.primary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .clip(
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navigator.navigate(TimerScreenDestination(longArrayOf(task.id)))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_str_launch), contentDescription = "Add entry")
        }
    }
}