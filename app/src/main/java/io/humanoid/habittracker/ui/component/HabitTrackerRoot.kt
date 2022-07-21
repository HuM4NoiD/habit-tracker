package io.humanoid.habittracker.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import io.humanoid.habittracker.ui.destinations.NavGraphs
import io.humanoid.habittracker.ui.destinations.bottomNavDestinations
import io.humanoid.habittracker.ui.destinations.bottomNavItems

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun HabitTrackerRoot(
    modifier: Modifier = Modifier
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController()
    navController.navigatorProvider.addNavigator(bottomSheetNavigator)

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        scrimColor = MaterialTheme.colors.surface.copy(alpha = 0.5f),
        sheetBackgroundColor = Color.Transparent,
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigator(
                    navController = navController,
                    navigationItems = bottomNavItems,
                    navigationDestinations = bottomNavDestinations
                )
            }
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                engine = rememberAnimatedNavHostEngine()
            )
        }
    }

}