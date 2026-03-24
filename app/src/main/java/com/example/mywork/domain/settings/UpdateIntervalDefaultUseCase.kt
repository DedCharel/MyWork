package com.example.mywork.domain.settings

import javax.inject.Inject

class UpdateIntervalDefaultUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(interval: Pair<Long,Long>){
        repository.updateIntervalDefault(interval)
    }
}