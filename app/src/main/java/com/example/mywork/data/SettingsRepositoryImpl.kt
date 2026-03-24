package com.example.mywork.data

import com.example.mywork.domain.settings.Settings
import com.example.mywork.domain.settings.SettingsRepository
import com.example.mywork.domain.worker.Worker
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {
    override fun getSettings(): Flow<Settings> {
        TODO("Not yet implemented")
    }

    override suspend fun updateWorkerDefault(workerId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updateIntervalDefault(interval: Pair<Long, Long>) {
        TODO("Not yet implemented")
    }
}