package com.example.onlineshopapp.repositories.site

import com.example.onlineshopapp.api.site.ContentApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.site.Content
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ContentRepository @Inject constructor(private val api: ContentApi) {
    suspend fun getContent(): ServiceResponse<Content> {
        return try {
            api.getContent()
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun getContentByTitle(title:String): ServiceResponse<Content> {
        return try {
            api.getContentByTitle(id = title)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}