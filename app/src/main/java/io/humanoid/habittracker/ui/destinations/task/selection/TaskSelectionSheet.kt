package io.humanoid.habittracker.ui.destinations.task.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.humanoid.habittracker.ui.destinations.task.list.TaskListViewModel


@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun TaskSelectionSheet(
    selectedTaskIds: LongArray,
    selectionNavigator: ResultBackNavigator<LongArray>
) {
    val viewModel = viewModel<TaskListViewModel>()
    val allTasks = viewModel.listLiveData.observeAsState()
    val selectedTasksMap = remember {
        SnapshotStateMap<Long, Int>().apply {
            for (taskId in selectedTaskIds) {
                put(taskId, 0)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp, 16.dp))
            .background(MaterialTheme.colors.surface)
    ) {

        Text(
            text = "Select Tasks",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            allTasks.value?.let { tasks ->
                items(
                    items = tasks
                ) { task ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        MaterialTheme.colors.primary,
                                        MaterialTheme.colors.secondary
                                    )
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = task.name,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(
                            checked = selectedTasksMap.containsKey(task.id),
                            onCheckedChange = {
                                if (it) {
                                    selectedTasksMap[task.id] = 0
                                } else {
                                    selectedTasksMap.remove(task.id)
                                }
                            }
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                selectionNavigator.navigateBack(selectedTasksMap.keys.toLongArray())
            },
            shape = CircleShape,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = if (selectedTasksMap.isEmpty()) "Clear Tasks" else "Add Tasks")
        }
    }
}