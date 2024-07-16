package com.example.onlineshopapp.viewmodels.invoices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.invoices.PaymentTransaction
import com.example.onlineshopapp.repositories.invoices.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
) : ViewModel() {

    fun goToPayment(
        data: PaymentTransaction,
        onResponse: (response: ServiceResponse<String>) -> Unit,
    ) {
        viewModelScope.launch {
            val response = repository.goToPayment(data)
            onResponse(response)
        }
    }
}