package io.humanoid.habittracker.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.humanoid.habittracker.R
import io.humanoid.habittracker.ui.destinations.NavMenuItem
import io.humanoid.habittracker.ui.destinations.destinations.TaskListScreenDestination

@Composable
fun AnimatedNavItem(
    navMenuItem: NavMenuItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) {
        MaterialTheme.colors.primary.copy(alpha = 0.3f)
    } else {
        Color.Transparent
    }
    val textColor = if (selected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable { onClick() }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = 3000f
                )
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = navMenuItem.iconId),
            contentDescription = navMenuItem.label,
            tint = textColor,
        )
        AnimatedVisibility(
            visible = selected,
        ) {
            Text(
                text = navMenuItem.label,
                style = MaterialTheme.typography.subtitle1.copy(color = textColor)
            )
        }
    }
}

@Preview
@Composable
fun NavItemPreview() {
    val state = remember {
        mutableStateOf(true)
    }
    AnimatedNavItem(
        navMenuItem = NavMenuItem(
            "Label",
            iconId = R.drawable.ic_str_launch,
            destination = TaskListScreenDestination
        ),
        selected = state.value,
        onClick = { state.value = state.value.not() }
    )
}