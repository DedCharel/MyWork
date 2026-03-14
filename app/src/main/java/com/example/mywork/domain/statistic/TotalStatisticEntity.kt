package com.example.mywork.domain.statistic

data class TotalStatisticEntity(
    val organizationId:  Long,
    val name: String,
    val totalTime: Double,
    val count: Int
)
