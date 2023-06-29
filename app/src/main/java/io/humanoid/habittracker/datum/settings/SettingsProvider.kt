package io.humanoid.habittracker.datum.settings

import kotlinx.coroutines.flow.Flow

interface SettingsProvider {
    fun getRepsDuration() : Flow<Int>
    suspend fun updateRepsDuration(durationInSeconds: Int)
}