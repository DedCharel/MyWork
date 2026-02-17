package com.example.mywork.domain.organization

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOrganizationUseCase @Inject constructor(
    private val repository: OrganizationRepository
) {
    operator fun invoke(): Flow<List<Organization>> {
        return repository.getAllOrganization()
    }
}