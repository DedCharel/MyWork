package com.example.mywork.data

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.work.Work
import com.example.mywork.domain.worker.Worker

fun WorkWithReferences.toWorkEntity(): Work {
    return Work(
        id = work.id,
        date = work.date,
        organization = organization.toOrganizationEntity(),
        worker = worker.toWorkerEntity(),
        description = work.description,
        time = work.time
    )
}

fun List<WorkWithReferences>.toWorkEntities(): List<Work> {
    return map { it.toWorkEntity() }
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

fun OrganizationDbModel.toOrganizationEntity(): Organization {
    return Organization(
        id = organizationId,
        name = name
    )
}

fun Organization.toDbModel(): OrganizationDbModel{
    return OrganizationDbModel(
        organizationId = id,
        name = name

    )
}

fun List<OrganizationDbModel>.toOrganizationEntities(): List<Organization>{
    return map { it.toOrganizationEntity() }
}

fun WorkerDbModel.toWorkerEntity(): Worker {
    return Worker(
        id = workerId,
        name = name
    )
}

fun Worker.toDbModel(): WorkerDbModel {
    return WorkerDbModel(
        workerId = id,
        name = name
    )
}

fun List<WorkerDbModel>.toWorkerEntities(): List<Worker>{
    return map { it.toWorkerEntity() }
}

