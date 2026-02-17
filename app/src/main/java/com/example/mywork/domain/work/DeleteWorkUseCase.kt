package com.example.mywork.domain.work

import com.example.mywork.domain.work.WorkRepository
import javax.inject.Inject

class DeleteWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(workId: Int){
        repository.deleteWork(workId)
    }
}