package io.humanoid.habittracker.datum.singleton

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import io.humanoid.habittracker.datum.converter.SettingsSerializer
import io.humanoid.habittracker.datum.model.AppSettings

object DataStoreProvider {
    private const val DATA_STORE_NAME = "settings"
    private val Context.dataStore: DataStore<AppSettings> by dataStore(
        fileName = "settings",
        serializer = SettingsSerializer,
    )

    private lateinit var _dataStore: DataStore<AppSettings>

    val dataStore by lazy {
        _dataStore
    }

    fun init(context: Context) {
        _dataStore = context.dataStore
    }
}