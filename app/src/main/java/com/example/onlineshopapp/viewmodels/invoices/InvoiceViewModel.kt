package com.example.onlineshopapp.viewmodels.invoices

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.invoices.Invoice
import com.example.onlineshopapp.repositories.invoices.InvoiceRepository
import com.example.onlineshopapp.utils.ThisApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val repository: InvoiceRepository,
) : ViewModel() {
    var token: String = ThisApp.token
    var userId: Long = ThisApp.userId
    var pageSize = 3
    var pageIndex = mutableStateOf(0)
    private var scrollPosition = 0

    var dataList = mutableStateOf<List<Invoice>>(listOf())
    var isLoading = mutableStateOf(true)

    init {
        getInvoiceByUserId(userId,pageIndex.value,pageSize) {response ->
            isLoading.value = false
            if (response.status == "OK"){
                dataList.value = response.data!!
            }
        }
    }


    fun getInvoiceById(
        id: Long,
        onResponse: (response: ServiceResponse<Invoice>) -> Unit
    ) {
        viewModelScope.launch {
            val response = repository.getInvoiceById(id, token)
            onResponse(response)
        }
    }

    fun getInvoiceByUserId(
        userId: Long,
        pageIndex: Int,
        pageSize: Int,
        onResponse: (response: ServiceResponse<Invoice>) -> Unit,
    ) {
        viewModelScope.launch {
            val response = repository.getInvoiceByUserId(userId, pageIndex, pageSize, token)
            onResponse(response)
        }
    }

    fun addInvoice(
        data: Invoice,
        onResponse: (response: ServiceResponse<Invoice>) -> Unit,
    ) {
        viewModelScope.launch {
            val response = repository.addInvoice(data, token)
            onResponse(response)
        }
    }

    fun incrementPage() {
        pageIndex.value += 1
    }

    fun onScrollPositionChange(position: Int) {
        scrollPosition = position
    }

    fun appendItems(items: List<Invoice>) {
        var current = ArrayList(dataList.value)
        current.addAll(items)
        dataList.value = current
    }

    fun nextPage() {
        viewModelScope.launch {
            if ((scrollPosition + 1) >= (pageIndex.value * pageSize)) {
                isLoading.value = true
                incrementPage()
                if (pageIndex.value > 0) {
                    getInvoiceByUserId(userId, pageIndex.value, pageSize) { response ->
                        if (response.status == "OK" && response.data!!.isNotEmpty()) {
                            appendItems(response.data!!)
                        }
                        isLoading.value = false
                    }
                }
            }
        }
    }
}