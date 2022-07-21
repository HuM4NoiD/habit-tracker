package io.humanoid.habittracker.ui.destinations.routine.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.humanoid.habittracker.datum.model.Routine
import io.humanoid.habittracker.ui.component.NumberInputRow
import io.humanoid.habittracker.ui.destinations.routine.list.RoutineListUiEvent
import io.humanoid.habittracker.ui.destinations.routine.list.RoutineListViewModel

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun RoutineInputSheet(
    navigator: DestinationsNavigator,
    routineToEdit: Routine? = null
) {

    val viewModel: RoutineListViewModel = viewModel()

    val nameState = remember {
        mutableStateOf(routineToEdit?.name ?: "")
    }
    val intervalState = remember {
        mutableStateOf(routineToEdit?.interval ?: 10)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
            )
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = {
                Text(text = "Name")
            }
        )
        NumberInputRow(
            intervalState = intervalState,
            unitLabel = "seconds",
            accessibilityLabel = "Interval",
            minMax = Pair(0, 30)
        )
        Button(
            modifier = Modifier
                .padding(16.dp),
            onClick = {
                val finalRoutine = routineToEdit?.copy(
                    name = nameState.value,
                    interval = intervalState.value
                ) ?: Routine(
                    name = nameState.value,
                    interval = intervalState.value
                )
                viewModel.onUiEvent(
                    RoutineListUiEvent.Insert(
                        finalRoutine
                    )
                )
                navigator.popBackStack()
            },
            shape = CircleShape
        ) {
            Text(text = if (routineToEdit == null) "Add Routine" else "Edit Routine")
        }
    }
}