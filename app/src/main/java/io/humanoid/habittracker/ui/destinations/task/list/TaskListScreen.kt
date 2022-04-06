package io.humanoid.habittracker.ui.destinations.task.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.humanoid.habittracker.ui.component.TaskListItem
import io.humanoid.habittracker.ui.destinations.destinations.TaskDetailScreenDestination
import io.humanoid.habittracker.ui.destinations.destinations.TaskInputSheetDestination

private const val TAG = "TaskListScreen"

@Composable
@Destination(route = "tasks", start = true)
fun TaskListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    val taskListViewModel: TaskListViewModel = viewModel()
    val taskListState = taskListViewModel.listLiveData.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Task List",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
            )
            Button(
                onClick = {
                    Log.d(TAG, "TaskListScreen: Adding task")
                    navigator.navigate(
                        TaskInputSheetDestination
                    )
                },
                shape = CircleShape
            ) {
                Text(text = "Add Task")
            }
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                taskListState.value?.let { tasks ->
                    items(
                        items = tasks
                    ) { task ->
                        TaskListItem(
                            task = task,
                            modifier = modifier,
                            onClick = {
                                navigator.navigate(
                                    TaskDetailScreenDestination(task.id)
                                )
                            },
                            onIconClick = {
                                taskListViewModel.onUiEvent(TaskListUiEvent.Delete(task.id))
                            }
                        )
                    }
                }
            }
        }
    }
}