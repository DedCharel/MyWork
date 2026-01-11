package com.example.mywork.domain

import javax.inject.Inject

class DeleteWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(workId: Int){
        repository.deleteWork(workId)
    }
}