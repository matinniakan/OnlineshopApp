package com.example.onlineshopapp.repositories.invoices

import com.example.onlineshopapp.api.invoices.TransactionApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.invoices.PaymentTransaction
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class TransactionRepository @Inject constructor(private val api: TransactionApi) {

    suspend fun goToPayment(
        data: PaymentTransaction,
    ): ServiceResponse<String> {
        return try {
            api.goToPayment(data)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}