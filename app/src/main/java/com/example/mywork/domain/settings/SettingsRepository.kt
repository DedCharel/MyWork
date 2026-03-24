package com.example.mywork.domain.settings

import com.example.mywork.domain.worker.Worker
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<Settings>

    suspend fun updateWorkerDefault(workerId: Long)

    suspend fun updateIntervalDefault(interval: Pair<Long, Long>)
}