package com.example.onlineshopapp.viewmodels.products


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.Product
import com.example.onlineshopapp.repositories.products.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    var dataList = mutableStateOf<List<Product>>(listOf())
    var isLoading = mutableStateOf(true)
    var data = mutableStateOf<Product?>(null)

    init {
        getProducts(0, 6) { response ->
            isLoading.value = false
            if (response.status == "OK".uppercase()) {
                dataList.value = response.data!!
            }
        }
    }

    fun getProducts(
        pageIndex: Int,
        pageSize: Int,
        onResponse: (response: ServiceResponse<Product>) -> Unit,
    ) {
        viewModelScope.launch {
            val response = repository.getProducts(pageIndex, pageSize)
            onResponse(response)
        }
    }

    fun getProductsByCategoryId(
        categoryId: Long,
        pageIndex: Int,
        pageSize: Int,
        onResponse: (response: ServiceResponse<Product>) -> Unit,
    ) {
        viewModelScope.launch {
            val response = repository.getProductsByCategoryId(categoryId, pageIndex, pageSize)
            onResponse(response)
        }
    }

    fun getProductById(id: Long, onResponse: (response: ServiceResponse<Product>) -> Unit) {
        viewModelScope.launch {
            val response = repository.getProductById(id)
            onResponse(response)
        }
    }

    fun getNewProduct(onResponse: (response: ServiceResponse<Product>) -> Unit) {
        viewModelScope.launch {
            val response = repository.getNewProducts()
            if (response.status == "OK".uppercase()) {
                dataList.value = response.data!!
            }
            onResponse(response)
        }
    }

    fun getPopularProducts(onResponse: (response: ServiceResponse<Product>) -> Unit) {
        viewModelScope.launch {
            val response = repository.getPopularProducts()
            if (response.status == "OK".uppercase()) {
                dataList.value = response.data!!
            }
            onResponse(response)
        }
    }
}