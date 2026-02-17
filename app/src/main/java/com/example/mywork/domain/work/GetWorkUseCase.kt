package com.example.mywork.domain.work

import com.example.mywork.domain.work.Work
import com.example.mywork.domain.work.WorkRepository
import javax.inject.Inject

class GetWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(workId: Int): Work {
        return  repository.getWork(workId)
    }
}