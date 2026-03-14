package com.example.mywork.data

import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.statistic.TotalStatisticEntity
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
        name = name,
        inn = inn,
        phone = phone,
        email = email,
        address = address,
        comments = comments
    )
}

fun Organization.toDbModel(): OrganizationDbModel{
    return OrganizationDbModel(
        organizationId = id,
        name = name,
        inn = inn,
        phone = phone,
        email = email,
        address = address,
        comments = comments

    )
}

fun List<OrganizationDbModel>.toOrganizationEntities(): List<Organization>{
    return map { it.toOrganizationEntity() }
}

fun WorkerDbModel.toWorkerEntity(): Worker {
    return Worker(
        id = workerId,
        name = name,
        phone = phone
    )
}

fun Worker.toDbModel(): WorkerDbModel {
    return WorkerDbModel(
        workerId = id,
        name = name,
        phone = phone
    )
}

fun List<WorkerDbModel>.toWorkerEntities(): List<Worker>{
    return map { it.toWorkerEntity() }
}

fun TotalStatistic.toEntity(): TotalStatisticEntity{
    return TotalStatisticEntity(
        organizationId, name, totalTime, count
    )
}

fun List<TotalStatistic>.toOrganizationStatisticEntities(): List<TotalStatisticEntity>{
    return map { it.toEntity() }
}

