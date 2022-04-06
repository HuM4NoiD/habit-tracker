package io.humanoid.habittracker.ui.destinations

import androidx.annotation.DrawableRes
import io.humanoid.habittracker.R
import io.humanoid.habittracker.ui.destinations.destinations.DirectionDestination
import io.humanoid.habittracker.ui.destinations.destinations.RoutineListScreenDestination
import io.humanoid.habittracker.ui.destinations.destinations.TaskListScreenDestination

data class NavMenuItem(
    val label: String,
    @DrawableRes
    val iconId: Int,
    val destination: DirectionDestination
)

val bottomNavItems = listOf(
    NavMenuItem(
        label = "Tasks",
        iconId = R.drawable.ic_str_task,
        destination = TaskListScreenDestination
    ),
    NavMenuItem(
        label = "Routines",
        iconId = R.drawable.ic_str_routines,
        destination = RoutineListScreenDestination
    ),
)

val bottomNavDestinations = hashSetOf(
    TaskListScreenDestination, RoutineListScreenDestination
)
