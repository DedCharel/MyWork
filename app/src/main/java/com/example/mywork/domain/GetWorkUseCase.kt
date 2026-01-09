package com.example.mywork.domain

class GetWorkUseCase(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(workId: Int): Work{
        return  repository.getWork(workId)
    }
}