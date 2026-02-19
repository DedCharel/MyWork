package com.example.mywork.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mywork.domain.work.Work

data class WorkWithReferences(
    @Embedded
    val work: WorkDbModel,

    @Relation(parentColumn = "organizationId", entityColumn = "organizationId")
    val organization: OrganizationDbModel,

    @Relation(parentColumn = "workerId", entityColumn = "workerId")
    val worker: WorkerDbModel
)
