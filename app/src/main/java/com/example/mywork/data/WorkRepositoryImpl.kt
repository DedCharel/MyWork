package com.example.mywork.data

import com.example.mywork.domain.work.Work
import com.example.mywork.domain.work.WorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkRepositoryImpl @Inject constructor(
    private val worksDao: WorksDao
) : WorkRepository {
    override fun getAllWorks(): Flow<List<Work>> {
        return worksDao.getAllWorks().map { it.toWorkEntities() }
    }

    override fun searchWork(query: String): Flow<List<Work>> {
       return worksDao.searchWork(query).map { it.toWorkEntities() }
    }

    override suspend fun addWork(
        date: Long,
        organizationId: Long,
        workerId: Long,
        description: String,
        time: Double
    ) {
        val workDBModel = WorkDbModel(0, date, organizationId, workerId, description, time)
        worksDao.addWork(workDBModel)
    }

    override suspend fun deleteWork(workId: Int) {
        worksDao.deleteWork(workId)
    }

    override suspend fun getWork(workId: Int): Work {
        return worksDao.getWork(workId).toWorkEntity()
    }

    override suspend fun editWork(work: Work) {
        worksDao.addWork(work.toDbModel())
    }
}