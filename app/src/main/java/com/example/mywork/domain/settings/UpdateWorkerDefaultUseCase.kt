package com.example.mywork.domain.settings

import com.example.mywork.domain.worker.Worker
import javax.inject.Inject

class UpdateWorkerDefaultUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(workerId: Long) {
        repository.updateWorkerDefault(workerId)
    }
}