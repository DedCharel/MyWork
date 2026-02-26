package com.example.mywork.domain.organization

import javax.inject.Inject

class AddOrganizationUseCase @Inject constructor(
    private val repository: OrganizationRepository
) {
    suspend operator fun invoke(
        name: String,
        inn: String,
        phone: String,
        email: String,
        address: String,
        comments: String){
        repository.addOrganization(
            name = name,
            inn = inn,
            phone =phone,
            email = email,
            address = address,
            comments = comments)
    }
}