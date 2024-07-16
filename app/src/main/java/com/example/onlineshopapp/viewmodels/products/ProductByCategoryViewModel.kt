package com.example.onlineshopapp.viewmodels.products


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.Product
import com.example.onlineshopapp.repositories.products.ProductRepository
import com.example.onlineshopapp.utils.ThisApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByCategoryViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {
    var categoryId: Long = ThisApp.productCategoryId
    var pageSize = 3
    var pageIndex = mutableStateOf(0)
    private var scrollPosition = 0

    var dataList = mutableStateOf<List<Product>>(listOf())
    var isLoading = mutableStateOf(true)

    init {
        getProductsByCategoryId(
            categoryId = categoryId,
            pageIndex = pageIndex.value,
            pageSize = pageSize
        ) { response ->
            isLoading.value = false
            if (response.status == "OK".uppercase()) {
                dataList.value = response.data!!
            }
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

    fun incrementPage() {
        pageIndex.value += 1
    }

    fun onScrollPositionChange(position: Int) {
        scrollPosition = position
    }

    fun appendItems(items: List<Product>) {
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
                    getProductsByCategoryId(categoryId, pageIndex.value, pageSize) { response ->
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