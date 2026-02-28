package com.example.mywork.domain.worker

import javax.inject.Inject

class AddWorkerUseCase @Inject constructor(
    private val repository: WorkerRepository
) {
    suspend operator fun invoke(name: String, phone: String){
        repository.addWorker(name, phone)
    }
}