package com.example.mywork.domain.organization

import kotlinx.coroutines.flow.Flow

interface OrganizationRepository {

    fun getAllOrganization(): Flow<List<Organization>>

    suspend fun getOrganization(organizationId: Long): Organization

    suspend fun addOrganization(
        name: String,
        inn: String,
        phone: String,
        email: String,
        address: String,
        comments: String)

    suspend fun editOrganization(organization: Organization)

    suspend fun deleteOrganization(organizationId: Long)
}