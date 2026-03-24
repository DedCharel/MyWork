package com.example.mywork.domain.settings

import com.example.mywork.domain.worker.Worker

data class Settings(
    val workerId: Long,
    val interval: Pair<Long,Long>
    )
