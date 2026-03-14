package com.example.mywork.domain.statistic

import com.example.mywork.domain.work.Work
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrganizationStatisticUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    operator fun invoke(organizationId:Long, range: Pair<Long, Long>): Flow<List<Work>> {
        return repository.getOrganizationStatistic(organizationId, range)
    }
}