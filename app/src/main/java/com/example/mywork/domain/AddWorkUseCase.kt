package com.example.mywork.domain

class AddWorkUseCase(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(work: Work){
        repository.addWork(work)
    }
}