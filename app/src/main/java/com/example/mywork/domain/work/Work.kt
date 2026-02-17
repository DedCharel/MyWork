package com.example.mywork.domain.work

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.Worker

data class Work(
    val id: Int,
    val date: Long,
    val organization: Organization,
    val worker: Worker,
    val description: String,
    val time: Long
)