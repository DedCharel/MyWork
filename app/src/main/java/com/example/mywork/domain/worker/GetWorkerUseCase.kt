package com.example.mywork.domain.worker

import javax.inject.Inject

class GetWorkerUseCase @Inject constructor(
    private val repository: WorkerRepository
) {
    suspend operator fun invoke(workerId: Long): Worker {
        return repository.getWorker(workerId)
    }
}