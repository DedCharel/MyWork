package com.example.mywork.domain.organization

import javax.inject.Inject

class DeleteOrganizationUseCase @Inject constructor(
    private val repository: OrganizationRepository
) {
    suspend operator fun invoke(organizationId: Long) {
        repository.deleteOrganization(organizationId)
    }
}