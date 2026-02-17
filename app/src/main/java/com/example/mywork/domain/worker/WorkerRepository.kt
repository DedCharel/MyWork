package com.example.mywork.domain.worker

import kotlinx.coroutines.flow.Flow

interface WorkerRepository {

    fun getAllWorkers(): Flow<List<Worker>>

    suspend fun addWorker(worker: Worker)

    suspend fun deleteWorker(workerId: Long)

    suspend fun editWorker(worker: Worker)

    suspend fun getWorker(workerId: Long):Worker
}