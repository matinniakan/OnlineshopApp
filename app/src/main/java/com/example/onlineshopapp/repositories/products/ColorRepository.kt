package com.example.onlineshopapp.repositories.products

import com.example.onlineshopapp.api.products.ColorApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.products.ProductColor
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ColorRepository @Inject constructor(private val api: ColorApi) {
    suspend fun getColors(): ServiceResponse<ProductColor> {
        return try {
            api.getColor()
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getColorById(id:Long): ServiceResponse<ProductColor> {
        return try {
            api.getColorById(id = id)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}