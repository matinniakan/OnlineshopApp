package com.example.onlineshopapp.api.products

import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.ProductCategory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductCategoryApi {
    @GET("/api/productCategory")
    suspend fun getCategory(): ServiceResponse<ProductCategory>

    @GET("/api/productCategory/{id}")
    suspend fun getCategoryById(@Path("id") id: Long): ServiceResponse<ProductCategory>
}