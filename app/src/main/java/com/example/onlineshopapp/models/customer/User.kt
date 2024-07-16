package com.example.onlineshopapp.models.customer

import com.example.onlineshopapp.db.models.UserEntity

data class User(
    var id: Long?,
    var customer: Customer?,
    var password: String?,
    var username: String?,
){
    fun convertToEntity(): UserEntity {
        return UserEntity(
            id = id?.toInt()!!,
            address = customer?.address,
            customerId = customer?.id!!,
            firstName = customer?.firstName,
            lastName = customer?.lastName,
            phone = customer?.phone,
            postalCode = customer?.postalCode,
            token = null,
            username = username,
            userId = id!!,
            password = password
        )
    }
}
