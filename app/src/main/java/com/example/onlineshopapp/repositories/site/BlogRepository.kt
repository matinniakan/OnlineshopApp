package com.example.onlineshopapp.repositories.site

import com.example.onlineshopapp.api.site.BlogApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.site.Blog
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class BlogRepository @Inject constructor(private val api: BlogApi) {
    suspend fun getBlog(): ServiceResponse<Blog> {
        return try {
            api.getBlog()
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getBlogById(id:Long): ServiceResponse<Blog> {
        return try {
            api.getBlogById(id = id)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}