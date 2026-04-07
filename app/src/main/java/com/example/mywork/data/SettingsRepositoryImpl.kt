package com.example.mywork.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mywork.domain.settings.Settings
import com.example.mywork.domain.settings.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl @Inject constructor(
    @param: ApplicationContext private val context: Context
) : SettingsRepository {

    private val workerDefaultKey = longPreferencesKey("worker_id")
    private val startIntervalDefaultKey = longPreferencesKey("start_interval")
    private val finishIntervalDefaultKey = longPreferencesKey("finish_interval")

    override fun getSettings(): Flow<Settings> {
        return context.dataStore.data.map { preferences ->
            val workerId = preferences[workerDefaultKey] ?: 0
            val interval = Pair(
                preferences[startIntervalDefaultKey] ?: 0,
                preferences[finishIntervalDefaultKey] ?: 0
            )
            Settings(
                workerId = workerId,
                interval = interval
            )
        }
    }

    override suspend fun updateWorkerDefault(workerId: Long) {
        context.dataStore.edit { preferences ->
            preferences[workerDefaultKey] = workerId
        }
    }

    override suspend fun updateIntervalDefault(interval: Pair<Long, Long>) {
        context.dataStore.edit { preferences ->
            preferences[startIntervalDefaultKey] = interval.first
            preferences[finishIntervalDefaultKey] = interval.second
        }
    }
}