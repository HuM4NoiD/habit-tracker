package io.humanoid.habittracker.ui.component

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Stopwatch(
    seconds: Long,
    centiSeconds: Long,
) {
    val factor = centiSeconds / 100f
    val color = MaterialTheme.colors.primary

    val tickInteractionSource = remember {
        MutableInteractionSource()
    }

    PeriodicRippleSideEffect(
        watchable = seconds,
        interactionSource = tickInteractionSource
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .size(200.dp)
            .indication(
                interactionSource = tickInteractionSource,
                indication = rememberRipple(
                    bounded = false,
                    color = MaterialTheme.colors.primary
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "$seconds",
                style = MaterialTheme.typography.h3
            )
            Text(
                text = "$centiSeconds".padStart(2, '0'),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun PeriodicRippleSideEffect(
    watchable: Any,
    interactionSource: MutableInteractionSource
) {
    LaunchedEffect(key1 = watchable) {
        val press = PressInteraction.Press(Offset.Zero)
        interactionSource.emit(press)
        delay(100)
        interactionSource.emit(PressInteraction.Release(press))
    }
}