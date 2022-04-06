package io.humanoid.habittracker.ui.destinations.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun TimerScreen(
    navigator: DestinationsNavigator,
    id: Long,
    isTask: Boolean
) {
    val viewModel = viewModel<TimerViewModel>(factory = TimerViewModel.Factory(id, isTask))
    val task by viewModel.taskLiveData.observeAsState()
    val routine by viewModel.routineLiveData.observeAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val title = if (isTask) task!!.name else routine!!.name
        Text(
            text = title,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(32.dp)
        )

    }
}