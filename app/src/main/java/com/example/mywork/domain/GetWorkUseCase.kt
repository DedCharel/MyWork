package com.example.mywork.domain

import javax.inject.Inject

class GetWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(workId: Int): Work{
        return  repository.getWork(workId)
    }
}