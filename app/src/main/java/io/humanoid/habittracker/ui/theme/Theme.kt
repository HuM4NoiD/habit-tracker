package io.humanoid.habittracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Rain,
    primaryVariant = Rain,
    secondary = Dawn,
    error = ErrorForDark,
    onError = OnErrorForDark,
    background = DarkBackground,
    surface = DarkBackground
)

private val LightColorPalette = lightColors(
    primary = RainDark,
    primaryVariant = RainDark,
    secondary = DawnDark,
    error = ErrorForLight,
    onError = OnErrorForLight

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun HabitTrackerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()

    val colors = if (darkTheme) {
        systemUiController.setStatusBarColor(DarkColorPalette.surface)
        systemUiController.setNavigationBarColor(Color.Transparent)
        DarkColorPalette
    } else {
        systemUiController.setStatusBarColor(LightColorPalette.surface)
        systemUiController.setNavigationBarColor(Color.Transparent)
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}