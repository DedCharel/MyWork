package com.example.mywork.data

import com.example.mywork.domain.worker.Worker
import com.example.mywork.domain.worker.WorkerRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkerRepositoryImpl @Inject constructor(
    private val workersDao: WorkersDao
): WorkerRepository {
    override fun getAllWorkers(): Flow<List<Worker>> {
     return workersDao.getAllWorkers().map { it.toWorkerEntities() }
    }

    override suspend fun addWorker(name: String, phone: String) {
        val workerDbModel = WorkerDbModel(
            workerId = 0,
            name = name,
            phone = phone
        )
        workersDao.addWorker(workerDbModel)
    }

    override suspend fun deleteWorker(workerId: Long) {
        workersDao.deleteWorker(workerId)
    }

    override suspend fun editWorker(worker: Worker) {
        workersDao.addWorker(worker.toDbModel())
    }

    override suspend fun getWorker(workerId: Long): Worker {
       return workersDao.getWorker(workerId).toWorkerEntity()
    }
}