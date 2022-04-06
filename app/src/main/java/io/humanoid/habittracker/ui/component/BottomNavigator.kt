package io.humanoid.habittracker.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.navigation.navigateTo
import io.humanoid.habittracker.R
import io.humanoid.habittracker.ui.destinations.NavMenuItem
import io.humanoid.habittracker.ui.destinations.destinations.DirectionDestination
import io.humanoid.habittracker.ui.destinations.destinations.TaskListScreenDestination
import io.humanoid.habittracker.ui.destinations.navDestination

@Composable
fun BottomNavigator(
    navController: NavHostController,
    navigationItems: List<NavMenuItem>,
    navigationDestinations: Set<DirectionDestination>
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val dest = backStackEntry?.navDestination

    AnimatedVisibility(
        visible = dest in navigationDestinations,
        enter = slideInVertically(initialOffsetY = {height -> height}),
        exit = slideOutVertically(targetOffsetY = {height -> height})
    ) {
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(16.dp)
            ) {
                navigationItems.forEach { item ->
                    AnimatedNavItem(
                        navMenuItem = item,
                        selected = dest == item.destination,
                        onClick = {
                            navController.navigateTo(item.destination) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigatorPreview() {
    BottomNavigator(
        navController = rememberNavController(), navigationItems = listOf(
            NavMenuItem("First", R.drawable.ic_play_left, TaskListScreenDestination),
            NavMenuItem("Second", R.drawable.ic_str_launch, TaskListScreenDestination),
            NavMenuItem("Third", R.drawable.ic_play_right, TaskListScreenDestination),
        ),
        navigationDestinations = hashSetOf(TaskListScreenDestination)
    )
}