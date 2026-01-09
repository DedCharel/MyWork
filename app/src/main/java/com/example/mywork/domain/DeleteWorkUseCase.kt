package com.example.mywork.domain

class DeleteWorkUseCase(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(workId: Int){
        repository.deleteWork(workId)
    }
}