package com.example.mywork.domain.organization

import javax.inject.Inject

class AddOrganizationUseCase @Inject constructor(
    private val repository: OrganizationRepository
) {
    suspend operator fun invoke(organization: Organization){
        repository.addOrganization(organization)
    }
}