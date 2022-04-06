package io.humanoid.habittracker.ui.component

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CountDownRing(
    progress: Float,
    seconds: Int
) {
    val color = MaterialTheme.colors.primary
    val base = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)

    val currentProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = FloatTweenSpec(easing = LinearOutSlowInEasing)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            drawCircle(
                color = base,
                style = Stroke(width = 24.dp.toPx())
            )
            drawArc(
                color = color,
                startAngle = 270f,
                sweepAngle = 360f * currentProgress,
                useCenter = false,
                style = Stroke(
                    width = 24.dp.toPx(),
                    cap = StrokeCap.Round
                ),
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$seconds",
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = "seconds",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
fun CountDownRingPreview() {
    val progress = remember {
        mutableStateOf(0f)
    }
    Column {
        CountDownRing(progress = progress.value, seconds = (progress.value * 100f).toInt())
        Button(
            onClick = {
                if (progress.value < 1f) progress.value += 0.1f
            }
        ) {
            Text(text = "Add")
        }
        Button(
            onClick = {
                if (progress.value > 0f) progress.value -= 0.1f
            }
        ) {
            Text(text = "Sub")
        }
    }
}