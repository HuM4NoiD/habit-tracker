package io.humanoid.habittracker.ui.destinations.task.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.humanoid.habittracker.R
import io.humanoid.habittracker.datum.model.Entry
import java.util.concurrent.ThreadLocalRandom

@Composable
@Destination
fun TaskDetailScreen(
    taskId: Long,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    val viewModel: TaskDetailViewModel = viewModel(factory = TaskDetailViewModel.Factory(taskId))
    val taskState = viewModel.taskState
    val task = taskState.value

    val random = ThreadLocalRandom.current()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 8.dp)
            )
            Text(
                text = task.desc,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            )
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = task.entries) { entry ->
                    Text(
                        text = "Entry: ${entry.count}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {
                viewModel.onUiEvent(
                    TaskDetailUiEvent.AddEntry(
                        Entry(count = random.nextInt(50))
                    )
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_str_launch), contentDescription = "Add entry")
        }
    }
}