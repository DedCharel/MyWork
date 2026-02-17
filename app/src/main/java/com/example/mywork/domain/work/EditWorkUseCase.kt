package com.example.mywork.domain.work

import com.example.mywork.domain.work.Work
import com.example.mywork.domain.work.WorkRepository
import javax.inject.Inject

class EditWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    suspend operator fun invoke(work: Work) {
        repository.editWork(work)
    }
}