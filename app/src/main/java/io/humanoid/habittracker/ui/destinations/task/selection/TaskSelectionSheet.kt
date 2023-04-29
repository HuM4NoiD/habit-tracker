package io.humanoid.habittracker.ui.destinations.task.selection

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.humanoid.habittracker.R
import io.humanoid.habittracker.di.appModule
import io.humanoid.habittracker.ui.destinations.task.list.TaskListViewModel

private const val TAG = "TaskSelectionSheet"

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun TaskSelectionSheet(
    selectedTaskIds: LongArray,
    selectionNavigator: ResultBackNavigator<LongArray>
) {
    val viewModel = viewModel<TaskListViewModel>()
    val allTasks = viewModel.listLiveData.observeAsState()
    Log.d(TAG, "TaskSelectionSheet: all tasks: ${allTasks.value?.map { it.id }}")
    val selectedTaskList = remember {
        mutableStateListOf<Pair<Long, String>>().apply {
            for (taskId in selectedTaskIds) {
                val t = appModule.domainModule.taskUseCases.getTask(taskId)
                t?.let { add(Pair(it.id, it.name)) }
            }
        }
    }
    
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp, 16.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        // Title
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text(
                text = "Select Tasks",
                style = MaterialTheme.typography.h4,
            )
            Button(
                shape = CircleShape,
                onClick = {
                    selectionNavigator.navigateBack(result = selectedTaskList.map { it.first }.toLongArray())
                }
            ) {
                Text(text = "Done", style = MaterialTheme.typography.button)
            }
        }

        // Task Chips
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            allTasks.value?.let { tasks ->
                items(
                    items = tasks
                ) { task ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .animateItemPlacement()
                            .clip(CircleShape)
                            .clickable {
                                selectedTaskList.add(Pair(task.id, task.name))
                            }
                            .border(
                                width = 1.dp,
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        MaterialTheme.colors.primary,
                                        MaterialTheme.colors.secondary
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(8.dp),
                    ) {
                        Text(
                            text = task.name,
                            style = MaterialTheme.typography.body1,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_str_plus),
                            contentDescription = "Add ${task.name}",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Selected Items
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(items = selectedTaskList, key = { index, _ -> index }) { index, task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .animateItemPlacement()
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
                        text = task.second,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        modifier = Modifier.size(16.dp),
                        onClick = {
                            selectedTaskList.removeAt(index)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_str_minus),
                            contentDescription = "Remove task ${task.second} from routine"
                        )
                    }
                }
            }
        }
    }
}