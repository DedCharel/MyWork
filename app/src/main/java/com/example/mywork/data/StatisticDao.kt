package com.example.mywork.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {


    @Transaction
    @Query(""" SELECT Organizations.name , SUM(Works.time) AS totalTime, COUNT(*) AS count
        FROM works 
        INNER JOIN organizations ON organizations.organizationId = works.organizationId
        GROUP BY Organizations.organizationId
        
    """)
    fun getAllOrganizationsStatistic(): Flow<List<OrganizationStatistic>>
}