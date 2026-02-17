package com.example.mywork.domain.work

import com.example.mywork.domain.work.Work
import kotlinx.coroutines.flow.Flow

interface WorkRepository {

    fun getAllWorks(): Flow<List<Work>>

    suspend fun addWork(
        date: Long,
        organizationId: Long,
        workerId: Long,
        description: String,
        time: Long
    )

    suspend fun deleteWork(workId: Int)

    suspend fun getWork(workId: Int): Work

    suspend fun editWork(work: Work)
}