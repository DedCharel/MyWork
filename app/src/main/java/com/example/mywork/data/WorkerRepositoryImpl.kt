package com.example.mywork.data

import com.example.mywork.domain.worker.Worker
import com.example.mywork.domain.worker.WorkerRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class WorkerRepositoryImpl @Inject constructor(
    private val workersDao: WorkersDao
): WorkerRepository {
    override fun getAllWorkers(): Flow<List<Worker>> {
        TODO("Not yet implemented")
    }

    override suspend fun addWorker(worker: Worker) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWorker(workerId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun editWorker(worker: Worker) {
        TODO("Not yet implemented")
    }

    override suspend fun getWorker(workerId: Long): Worker {
        TODO("Not yet implemented")
    }
}