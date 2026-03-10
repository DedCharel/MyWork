package com.example.mywork.domain.statistic

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrganizationStatisticUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    operator fun invoke(range: Pair<Long,Long>): Flow<List<OrganizationStatisticEntity>>{
        return repository.getAllOrganizationStatistic(range)
    }
}