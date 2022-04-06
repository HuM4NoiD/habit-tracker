package io.humanoid.habittracker.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.humanoid.habittracker.R

@Composable
fun NumberInputRow(
    intervalState: MutableState<Int>,
    unitLabel: String,
    accessibilityLabel: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                if (intervalState.value > 0) {
                    intervalState.value -= 1
                }
            },
            modifier = Modifier.size(72.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_left),
                contentDescription = "Decrease $accessibilityLabel",
                modifier = Modifier.size(36.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = intervalState.value.toString(),
                style = MaterialTheme.typography.h4
            )
            Text(
                text = unitLabel,
                style = MaterialTheme.typography.caption
            )
        }
        IconButton(
            onClick = {
                if (intervalState.value < 30) {
                    intervalState.value += 1
                }
            },
            modifier = Modifier.size(72.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_right),
                contentDescription = "Increase $accessibilityLabel",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview
@Composable
fun NumberInputRowPreview() {

    NumberInputRow(
        intervalState = remember { mutableStateOf(10) },
        unitLabel = "Units",
        accessibilityLabel = "Units"
    )
}