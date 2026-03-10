package com.example.mywork.data

import com.example.mywork.domain.statistic.OrganizationStatisticEntity
import com.example.mywork.domain.statistic.StatisticRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StatisticRepositoryImpl @Inject constructor(
    private val statisticDao: StatisticDao
) : StatisticRepository {
    override fun getAllOrganizationStatistic(range:Pair<Long,Long>): Flow<List<OrganizationStatisticEntity>> {
        return statisticDao.getAllOrganizationsStatistic(range.first, range.second).map { it.toOrganizationStatisticEntities() }
    }
}