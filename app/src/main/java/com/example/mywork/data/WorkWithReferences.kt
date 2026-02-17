package com.example.mywork.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mywork.domain.work.Work

data class WorkWithReferences(
    @Embedded
    val work: Work,
    @Relation(parentColumn = "organisationId", entityColumn = "organisationId")
    val organization: OrganizationDbModel,

    @Relation(parentColumn = "workerId", entityColumn = "workerId")
    val worker: WorkerDbModel
)
