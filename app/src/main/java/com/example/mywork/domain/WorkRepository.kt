package com.example.mywork.domain

import kotlinx.coroutines.flow.Flow

interface WorkRepository {

    fun getAllWorks(): Flow<List<Work>>

    suspend fun addWork(
        date: Long,
        counterparty: String,
        worker: String,
        description: String,
        time: Long
    )

    suspend fun deleteWork(workId: Int)

    suspend fun getWork(workId: Int): Work

    suspend fun editWork(work: Work)
}