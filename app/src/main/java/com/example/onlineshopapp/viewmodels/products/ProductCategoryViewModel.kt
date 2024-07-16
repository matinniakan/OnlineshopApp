package com.example.onlineshopapp.viewmodels.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.ProductCategory
import com.example.onlineshopapp.repositories.products.ProductCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCategoryViewModel @Inject constructor(
    private val repository: ProductCategoryRepository,
) : ViewModel() {

    var dataList = mutableStateOf<List<ProductCategory>>(listOf())
    var isLoading = mutableStateOf(true)

    init {
        getCategories { response ->
            isLoading.value = false
            if (response.status == "OK".uppercase()) {
                dataList.value = response.data!!
            }
        }
    }

    fun getCategories(onResponse: (response: ServiceResponse<ProductCategory>) -> Unit) {
        viewModelScope.launch {
            val response = repository.getCategory()
            onResponse(response)
        }
    }

    fun getCategoryById(
        id: Long,
        onResponse: (response: ServiceResponse<ProductCategory>) -> Unit,
    ) {
        viewModelScope.launch {
            val response = repository.getCategoryById(id)
            onResponse(response)
        }
    }
}