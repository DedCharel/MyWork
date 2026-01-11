package com.example.mywork.domain

import javax.inject.Inject

class EditWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(work: Work) {
        repository.editWork(work)
    }
}

