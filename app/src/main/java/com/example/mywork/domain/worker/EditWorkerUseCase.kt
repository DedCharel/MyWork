package com.example.mywork.domain.worker

import javax.inject.Inject

class EditWorkerUseCase @Inject constructor(
    private val repository: WorkerRepository
) {
    suspend operator fun invoke(worker: Worker){
        repository.editWorker(worker)
    }
}