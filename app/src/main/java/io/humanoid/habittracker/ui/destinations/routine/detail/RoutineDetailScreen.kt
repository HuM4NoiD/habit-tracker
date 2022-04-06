package io.humanoid.habittracker.ui.destinations.routine.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import io.humanoid.habittracker.datum.model.Routine

@Composable
@Destination
fun RoutineDetailScreen(
    routine: Routine,
    modifier: Modifier = Modifier
) {
    Text(
        text = routine.toString(),
        modifier = modifier.fillMaxSize()
            .padding(32.dp),
        textAlign = TextAlign.Justify,
        style = TextStyle(fontSize = 30.sp)
    )
}