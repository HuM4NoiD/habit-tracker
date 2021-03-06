package io.humanoid.habittracker.ui.destinations.entryinput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.humanoid.habittracker.ui.component.NumberInputRow
import io.humanoid.habittracker.datum.dto.IdCount

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun RepsEntryInputSheet(
    resultNavigator: ResultBackNavigator<IdCount>,
    taskId: Long,
    shouldPopTwice: Boolean
) {

    val viewModel: EntryInputViewModel = viewModel(factory = EntryInputViewModel.Factory(taskId))
    val intervalState = viewModel.entryState
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
            )
            .background(MaterialTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberInputRow(
            intervalState = intervalState,
            unitLabel = "reps",
            accessibilityLabel = "Reps"
        )
        Button(
            onClick = {
                resultNavigator.navigateBack(IdCount(taskId, intervalState.value))
            }
        ) {
            Text(text = "Add Entry")
        }
    }
}