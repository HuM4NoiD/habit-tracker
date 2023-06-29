package io.humanoid.habittracker.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.navigation.navigateTo
import io.humanoid.habittracker.R
import io.humanoid.habittracker.ui.destinations.bottomNavDestinations
import io.humanoid.habittracker.ui.destinations.destinations.Destination
import io.humanoid.habittracker.ui.destinations.destinations.RoutineListScreenDestination
import io.humanoid.habittracker.ui.destinations.destinations.SettingsScreenDestination
import io.humanoid.habittracker.ui.destinations.destinations.TaskListScreenDestination
import io.humanoid.habittracker.ui.destinations.navDestination

@Composable
fun TopBar(
    navController: NavHostController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.navDestination

    val context = LocalContext.current

    AnimatedVisibility(
        visible = currentDestination in bottomNavDestinations,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
        ) {
            Text(
                text = getTitle(currentDestination),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.weight(1F)
            )
            IconButton(
                onClick = {
                    navController.navigateTo(SettingsScreenDestination)
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_str_gear), contentDescription = "Settings")
            }
        }
    }
}

fun getTitle(destination: Destination?): String {
    return when(destination) {
        is TaskListScreenDestination -> "Tasks"
        is RoutineListScreenDestination -> "Routines"
        else -> ""
    }
}
