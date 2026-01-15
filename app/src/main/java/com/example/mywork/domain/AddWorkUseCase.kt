package com.example.mywork.domain

import javax.inject.Inject
import kotlin.Long

class AddWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(
        date: Long,
        counterparty: String,
        worker: String,
        description: String,
        time: Long
    ) {
        repository.addWork(date, counterparty, worker, description, time)
    }
}