package com.example.mywork.data

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.work.Work
import com.example.mywork.domain.Worker

fun WorkWithReferences.toEntity(): Work {
    return Work(
        id = work.id,
        date = work.date,
        organization = organization.toEntity(),
        worker = worker.toEntity(),
        description = work.description,
        time = work.time
    )
}

fun List<WorkWithReferences>.toEntities(): List<Work> {
    return map { it.toEntity() }
}

fun Work.toDbModel(): WorkDbModel {
    return WorkDbModel(
        id = id,
        date = date,
        organizationId = organization.id,
        workerId = worker.id,
        description = description,
        time = time
    )
}

fun OrganizationDbModel.toEntity(): Organization {
    return Organization(
        id = organizationId,
        name = name
    )
}

fun WorkerDbModel.toEntity(): Worker {
    return Worker(
        id = workerId,
        name = name
    )
}

