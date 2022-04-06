package io.humanoid.habittracker.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colors.secondary.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
    }

    val textColor = if (selected) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.onBackground
    }

    var modifier = Modifier
        .animateContentSize()
        .background(
            color = backgroundColor,
            shape = CircleShape
        )
        .clip(CircleShape);

    if (selected) {
        modifier = modifier.border(
            width = 1.dp, color = textColor,
            shape = CircleShape
        )
    }

    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1.copy(color = textColor)
        )
    }
}

@Preview
@Composable
fun SelectionChipPreview() {
    val state = remember {
        mutableStateOf(true)
    }
    SelectionChip(label = "This is a chip", selected = state.value, onClick = {state.value = state.value.not()})
}