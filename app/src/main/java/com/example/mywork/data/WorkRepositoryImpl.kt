package com.example.mywork.data

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.work.Work
import com.example.mywork.domain.work.WorkRepository
import com.example.mywork.domain.worker.Worker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkRepositoryImpl @Inject constructor(
    private val worksDao: WorksDao
) : WorkRepository {
    override fun getAllWorks(): Flow<List<Work>> {
        return worksDao.getAllWorks().map { it.toEntities() }
    }

    override suspend fun addWork(
        date: Long,
        organizationId: Long,
        workerId: Long,
        description: String,
        time: Long
    ) {
        val workDBModel = WorkDbModel(0, date, organizationId, workerId, description, time)
        worksDao.addWork(workDBModel)
    }

    override suspend fun deleteWork(workId: Int) {
        worksDao.deleteWork(workId)
    }

    override suspend fun getWork(workId: Int): Work {
        return worksDao.getWork(workId).toEntity()
    }

    override suspend fun editWork(work: Work) {
        worksDao.addWork(work.toDbModel())
    }
}