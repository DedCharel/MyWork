package com.example.mywork.data

import com.example.mywork.domain.Work

fun WorkDbModel.toEntity(): Work {
    return Work(
        id = id,
        date = date,
        counterparty = counterparty,
        worker = worker,
        description = description,
        time = time
    )
}

fun List<WorkDbModel>.toEntities(): List<Work> {
    return map { it.toEntity() }
}

fun Work.toDbModel(): WorkDbModel {
    return WorkDbModel(
        id = id,
        date = date,
        counterparty = counterparty,
        worker = worker,
        description = description,
        time = time
    )
}

