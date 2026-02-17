package com.example.mywork.domain.worker

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWorkersUseCase @Inject constructor(
    private val repository: WorkerRepository
) {
    operator fun invoke(): Flow<List<Worker>> {
        return repository.getAllWorkers()
    }
}