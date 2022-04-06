package io.humanoid.habittracker.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import io.humanoid.habittracker.datum.model.Routine

@Composable
fun RoutineListItem(
    routine: Routine,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(MaterialTheme.colors.primary, MaterialTheme.colors.secondary)),
                shape = RoundedCornerShape(8.dp)
            ).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = routine.name,
            modifier = modifier.weight(1f),
            style = MaterialTheme.typography.h5
        )
        Icon(
            imageVector = Icons.TwoTone.AccountCircle, contentDescription = "Routine",
            modifier = Modifier.clickable { onIconClick() }
        )
    }
}