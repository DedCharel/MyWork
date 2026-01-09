package com.example.mywork.domain

import kotlinx.coroutines.flow.Flow

class GetAllWorksUseCase (
    private val repository: WorkRepository
) {
    operator fun invoke(): Flow<List<Work>> {
        return repository.getAllWorks()
    }
}