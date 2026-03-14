package com.example.mywork.domain.statistic

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.work.Work
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {

    fun getAllOrganizationStatistic(range: Pair<Long, Long>): Flow<List<TotalStatisticEntity>>

    fun getOrganizationStatistic(organizationId: Long, range: Pair<Long, Long>): Flow<List<Work>>
}