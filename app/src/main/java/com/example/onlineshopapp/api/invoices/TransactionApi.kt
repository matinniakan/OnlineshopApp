package com.example.onlineshopapp.api.invoices


import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.invoices.PaymentTransaction
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionApi {

    @POST("/api/trx/gotoPayment")
    suspend fun goToPayment(@Body data: PaymentTransaction): ServiceResponse<String>
}