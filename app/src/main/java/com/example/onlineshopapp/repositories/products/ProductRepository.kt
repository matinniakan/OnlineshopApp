package com.example.onlineshopapp.repositories.products

import com.example.onlineshopapp.api.products.ProductApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.Product
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ProductRepository @Inject constructor(private val api: ProductApi) {
    suspend fun getProducts(pageIndex: Int, pageSize: Int): ServiceResponse<Product> {
        return try {
            api.getProduct(pageIndex, pageSize)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getProductsByCategoryId(
        categoryId: Long,
        pageIndex: Int,
        pageSize: Int
    ): ServiceResponse<Product> {
        return try {
            api.getProductsByCategoryId(categoryId, pageIndex, pageSize)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getProductById(id: Long): ServiceResponse<Product> {
        return try {
            api.getProductById(id = id)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getNewProducts(): ServiceResponse<Product> {
        return try {
            api.getNewProducts()
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getPopularProducts(): ServiceResponse<Product> {
        return try {
            api.getPopularProducts()
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}