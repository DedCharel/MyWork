package com.example.mywork.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {


    @Transaction
    @Query(""" SELECT Organizations.organizationId,  Organizations.name , CAST(ROUND(SUM(Works.time),2) as DECEMAL(10,2)) AS totalTime, COUNT(*) AS count
        FROM works         
        INNER JOIN organizations ON organizations.organizationId = works.organizationId
        WHERE works.date >= :startRange AND works.date <= :finishRange
        GROUP BY Organizations.organizationId ORDER BY totalTime DESC
        
    """)
    fun getAllOrganizationsStatistic(startRange: Long, finishRange:Long): Flow<List<TotalStatistic>>


    @Transaction
    @Query ("""SELECT * FROM works 
        WHERE organizationId = :organizationId 
        AND works.date >= :startRange AND works.date <= :finishRange
        ORDER BY time DESC""")
    fun getOrganizationStatistic(organizationId: Long,startRange: Long, finishRange:Long): Flow<List<WorkWithReferences>>
}