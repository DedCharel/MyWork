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
        return organizationsDao.getOrganizations().map { it.toOrganizationEntities() }
    }

    override suspend fun getOrganization(organizationId: Long): Organization {
        return organizationsDao.getOrganization(organizationId).toOrganizationEntity()
    }

    override suspend fun addOrganization(
        name: String,
        inn: String,
        phone: String,
        email: String,
        address: String,
        comments: String
    ) {
        val organizationDbModel = OrganizationDbModel(
            organizationId = 0,
            name = name,
            inn = inn,
            phone = phone,
            email = email,
            address = address,
            comments = comments
        )
        organizationsDao.addOrganization(organizationDbModel)
    }

    override suspend fun editOrganization(organization: Organization) {
        organizationsDao.addOrganization(organization.toDbModel())
    }

    override suspend fun deleteOrganization(organizationId: Long) {
        organizationsDao.deleteOrganization(organizationId)
    }
}