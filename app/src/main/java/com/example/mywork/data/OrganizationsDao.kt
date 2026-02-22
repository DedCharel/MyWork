package com.example.mywork.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface OrganizationsDao {

    @Transaction
    @Query("SELECT * FROM organizations ORDER BY name ASC")
    fun getOrganizations(): Flow<List<OrganizationDbModel>>

    @Transaction
    @Query("SELECT * FROM organizations WHERE organizationId ==:organizationId")
    suspend fun getOrganization(organizationId: Long): OrganizationDbModel

    @Transaction
    @Query("DELETE FROM organizations WHERE organizationId ==:organizationId")
    suspend fun deleteOrganization(organizationId: Long)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrganization(organization: OrganizationDbModel)


}