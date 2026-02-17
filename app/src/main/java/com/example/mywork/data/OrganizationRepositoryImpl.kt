package com.example.mywork.data

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.organization.OrganizationRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationsDao: OrganizationsDao
): OrganizationRepository {
    override fun getAllOrganization(): Flow<List<Organization>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrganization(organizationId: Long): Organization {
        TODO("Not yet implemented")
    }

    override suspend fun addOrganization(organization: Organization) {
        TODO("Not yet implemented")
    }

    override suspend fun editOrganization(organization: Organization) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrganization(organizationId: Long) {
        TODO("Not yet implemented")
    }
}