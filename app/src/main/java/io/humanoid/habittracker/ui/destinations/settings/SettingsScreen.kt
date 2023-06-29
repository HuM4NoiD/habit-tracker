package io.humanoid.habittracker.ui.destinations.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.humanoid.habittracker.di.dataAccessModule


@Composable
@Destination(route = "settings")
fun SettingsScreen(
    navigator: DestinationsNavigator
) {
    val settingsProvider = dataAccessModule.settingsProvider
    val settings by settingsProvider.getSettings().collectAsState(initial = null)

    val coroutineScope = rememberCoroutineScope()

    SettingsScreenContent()
}

@Composable
fun SettingsScreenContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .weight(1f)
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 32.dp)
        )
    }
}