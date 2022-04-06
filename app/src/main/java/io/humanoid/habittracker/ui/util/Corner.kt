package io.humanoid.habittracker.ui.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Corner(
    val size: Dp = 0.dp,
    val type: Type = Type.NONE
) {
    enum class Type {
        CUT, ROUND, NONE
    }
}
