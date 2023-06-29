package io.humanoid.habittracker.datum.settings.impl

import androidx.datastore.core.DataStore
import io.humanoid.habittracker.datum.model.AppSettings
import io.humanoid.habittracker.datum.settings.SettingsProvider
import kotlinx.coroutines.flow.Flow

class SettingsProviderImpl(
    private val dataStore: DataStore<AppSettings>
) : SettingsProvider {

    override fun getSettings(): Flow<AppSettings> {
        return dataStore.data
    }

    override suspend fun updateRepsDuration(durationInSeconds: Int) {
        dataStore.updateData { settings ->
            settings.copy(repsDuration = durationInSeconds)
        }
    }

    companion object {
        const val REPS_DURATION = "repsDuration"
    }
}