package com.example.mywork.data

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.organization.OrganizationRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationsDao: OrganizationsDao
): OrganizationRepository {
    override fun getAllOrganization(): Flow<List<Organization>> {
        return organizationsDao.getOrganizations().map { it.toEntities() }
    }

    override suspend fun getOrganization(organizationId: Long): Organization {
        return organizationsDao.getOrganization(organizationId).toEntity()
    }

    override suspend fun addOrganization(organization: Organization) {
        organizationsDao.addOrganization(organization.toDbModel())
    }

    override suspend fun editOrganization(organization: Organization) {
        organizationsDao.addOrganization(organization.toDbModel())
    }

    override suspend fun deleteOrganization(organizationId: Long) {
        organizationsDao.deleteOrganization(organizationId)
    }
}