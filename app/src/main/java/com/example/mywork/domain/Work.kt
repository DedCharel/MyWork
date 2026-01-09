package com.example.mywork.domain

data class Work(
    val id: Int,
    val date: Long,
    val counterparty: String,
    val worker: String,
    val description: String,
    val time: Long
)
