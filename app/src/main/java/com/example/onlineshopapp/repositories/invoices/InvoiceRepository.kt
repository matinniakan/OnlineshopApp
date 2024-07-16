package com.example.onlineshopapp.repositories.invoices

import com.example.onlineshopapp.api.invoices.InvoiceApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.invoices.Invoice
import com.example.onlineshopapp.repositories.base.BaseRepository
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class InvoiceRepository @Inject constructor(private val api: InvoiceApi) : BaseRepository() {

    suspend fun getInvoiceById(
        id: Long,
        token: String,
    ): ServiceResponse<Invoice> {
        return try {
            api.getInvoiceById(id = id, prepareToken(token))
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getInvoiceByUserId(
        userId: Long,
        pageIndex: Int,
        pageSize: Int,
        token: String,
    ): ServiceResponse<Invoice> {
        return try {
            api.getInvoiceByUserId(id = userId, pageIndex, pageSize, prepareToken(token))
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun addInvoice(
        data: Invoice,
        token: String,
    ): ServiceResponse<Invoice> {
        return try {
            api.addInvoice(data, prepareToken(token))
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}