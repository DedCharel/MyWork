package com.example.mywork.domain.work

import com.example.mywork.domain.work.Work
import com.example.mywork.domain.work.WorkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWorksUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    operator fun invoke(): Flow<List<Work>> {
        return repository.getAllWorks()
    }
}