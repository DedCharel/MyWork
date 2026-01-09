package com.example.mywork.domain

class EditWorkUseCase(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(work: Work) {
        repository.editWork(work)
    }
}

