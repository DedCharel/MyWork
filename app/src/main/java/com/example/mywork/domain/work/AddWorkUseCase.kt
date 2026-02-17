package com.example.mywork.domain.work

import com.example.mywork.domain.work.WorkRepository
import javax.inject.Inject

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