package com.example.onlineshopapp.api.products

import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.ProductColor
import retrofit2.http.GET
import retrofit2.http.Path

interface ColorApi {
    @GET("/api/color")
    suspend fun getColor(): ServiceResponse<ProductColor>

    @GET("/api/color/{id}")
    suspend fun getColorById(@Path("id") id: Long): ServiceResponse<ProductColor>
}