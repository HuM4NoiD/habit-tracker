package io.humanoid.habittracker.ui.destinations.routine.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import io.humanoid.habittracker.ui.component.RoutineListItem
import io.humanoid.habittracker.ui.destinations.destinations.RoutineDetailScreenDestination
import io.humanoid.habittracker.ui.destinations.destinations.RoutineInputSheetDestination

@Composable
@Destination(route = "routines", start = false)
fun RoutineListScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    val viewModel: RoutineListViewModel = viewModel()
    val listState = viewModel.listLiveData.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navigator.navigate(RoutineInputSheetDestination())
                },
                shape = CircleShape
            ) {
                Text(text = "Add Routine")
            }
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listState.value?.let { items ->
                    items(
                        items = items
                    ) { routine ->
                        RoutineListItem(
                            routine = routine,
                            modifier = modifier,
                            onClick = {
                                navigator.navigate(RoutineDetailScreenDestination(routine.id))
                            },
                            onIconClick = {
                                viewModel.onUiEvent(RoutineListUiEvent.Delete(routine.id))
                            }
                        )
                    }
                }
            }
        }
    }
}