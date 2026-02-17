package com.example.mywork.domain.organization

import javax.inject.Inject

class GetOrganizationUseCase @Inject constructor(
    private val repository: OrganizationRepository
) {

    suspend operator fun invoke(organizationId: Long): Organization {
        return repository.getOrganization(organizationId)
    }
}