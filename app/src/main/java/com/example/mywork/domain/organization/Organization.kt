package com.example.mywork.domain.organization

data class Organization(
    val id: Long,
    val name: String,
    val inn: String,
    val phone: String,
    val email: String,
    val address: String,
    val comments: String
)