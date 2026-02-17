package com.example.mywork.domain.organization

import javax.inject.Inject

class EditOrganizationUseCase @Inject constructor(
    private val repository: OrganizationRepository
) {
    suspend operator fun invoke(organization: Organization){
        repository.editOrganization(organization)
    }
}