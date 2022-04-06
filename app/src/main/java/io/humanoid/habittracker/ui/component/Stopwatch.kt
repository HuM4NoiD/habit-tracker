package io.humanoid.habittracker.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun Stopwatch(
    seconds: Long,
    centiSeconds: Long,
) {
    val factor = centiSeconds / 100f
    val color = MaterialTheme.colors.primary
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(16.dp),
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            drawCircle(
                color = color.copy(alpha = max(0f, 0.9f - factor)),
                radius = size.minDimension * factor / 2
            )
        }
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