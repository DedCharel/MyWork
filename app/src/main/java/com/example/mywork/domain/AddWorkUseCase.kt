package com.example.mywork.domain

import javax.inject.Inject

class AddWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(work: Work){
        repository.addWork(work)
    }
}