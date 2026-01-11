package com.example.mywork.data

import com.example.mywork.domain.Work
import com.example.mywork.domain.WorkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkRepositoryImpl @Inject constructor(
    private val worksDao: WorksDao
): WorkRepository {
    override fun getAllWorks(): Flow<List<Work>> {
        TODO("Not yet implemented")
    }

    override suspend fun addWork(work: Work) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWork(workId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getWork(workId: Int): Work {
        TODO("Not yet implemented")
    }

    override suspend fun editWork(work: Work) {
        TODO("Not yet implemented")
    }
}