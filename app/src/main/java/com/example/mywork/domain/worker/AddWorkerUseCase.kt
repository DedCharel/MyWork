package com.example.mywork.domain.worker

import javax.inject.Inject

class AddWorkerUseCase @Inject constructor(
    private val repository: WorkerRepository
) {
    suspend operator fun invoke(worker: Worker){
        repository.addWorker(worker)
    }
}