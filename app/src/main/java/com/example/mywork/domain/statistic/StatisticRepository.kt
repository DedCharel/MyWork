package com.example.mywork.domain.statistic

import kotlinx.coroutines.flow.Flow

interface StatisticRepository {

    fun getAllOrganizationStatistic(range: Pair<Long, Long>): Flow<List<TotalStatisticEntity>>
}