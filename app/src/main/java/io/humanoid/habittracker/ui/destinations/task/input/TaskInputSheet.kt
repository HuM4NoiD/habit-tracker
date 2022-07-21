package io.humanoid.habittracker.ui.destinations.task.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.humanoid.habittracker.datum.model.Task
import io.humanoid.habittracker.datum.model.TaskType
import io.humanoid.habittracker.ui.component.SelectionChip
import io.humanoid.habittracker.ui.destinations.task.list.TaskListUiEvent
import io.humanoid.habittracker.ui.destinations.task.list.TaskListViewModel

private const val TAG = "TaskInputDialog"

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun TaskInputSheet(
    navigator: DestinationsNavigator,
    taskToEdit: Task? = null
) {
    val nameState = remember {
        mutableStateOf(taskToEdit?.name ?: "")
    }
    val descState = remember {
        mutableStateOf(taskToEdit?.desc ?: "")
    }
    val taskTypeState = remember {
        mutableStateOf(taskToEdit?.type ?: TaskType.REPS)
    }
    val viewModel: TaskListViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
            )
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nameState.value,
            onValueChange = {nameState.value = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "Name")
            },
        )
        TextField(
            value = descState.value,
            onValueChange = { descState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "Description")
            },
        )

        if (taskToEdit == null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SelectionChip(
                    label = "Duration",
                    selected = taskTypeState.value == TaskType.DURATION,
                    onClick = {
                        taskTypeState.value = TaskType.DURATION
                    }
                )
                SelectionChip(
                    label = "Reps",
                    selected = taskTypeState.value == TaskType.REPS,
                    onClick = {
                        taskTypeState.value = TaskType.REPS
                    },
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(16.dp),
            onClick = {
                val finalTask = taskToEdit?.copy(
                    name = nameState.value,
                    desc = descState.value,
                    type = taskTypeState.value
                ) ?: Task(
                    name = nameState.value,
                    desc = descState.value,
                    type = taskTypeState.value
                )
                viewModel.onUiEvent(
                    TaskListUiEvent.Insert(
                        finalTask
                    )
                )
                navigator.popBackStack()
            },
            shape = CircleShape
        ) {
            Text(text = if (taskToEdit == null) "Add Task" else "Edit Task")
        }
    }
}

@Preview
@Composable
fun TaskInputSheetPreview() {
    TaskInputSheet(
        navigator = EmptyDestinationsNavigator,
        taskToEdit = null
    )
}