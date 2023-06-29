package io.humanoid.habittracker.datum.settings

import io.humanoid.habittracker.datum.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsProvider {
    fun getSettings(): Flow<AppSettings>
    suspend fun updateRepsDuration(durationInSeconds: Int)
}