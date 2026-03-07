package com.example.mywork.domain.work

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchWorkUseCase @Inject constructor(
    private val repository: WorkRepository
) {
    operator fun invoke(query: String): Flow<List<Work>> {
        return repository.searchWork(query)
    }
}