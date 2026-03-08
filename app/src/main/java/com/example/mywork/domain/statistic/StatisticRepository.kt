package com.example.mywork.domain.statistic

import kotlinx.coroutines.flow.Flow

interface StatisticRepository {

    fun getAllOrganizationStatistic(): Flow<List<OrganizationStatisticEntity>>
}