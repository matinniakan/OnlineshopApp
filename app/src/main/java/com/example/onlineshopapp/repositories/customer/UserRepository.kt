package com.example.onlineshopapp.repositories.customer

import com.example.onlineshopapp.api.customer.UserApi
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.customer.User
import com.example.onlineshopapp.models.customer.UserVM
import com.example.onlineshopapp.repositories.base.BaseRepository
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(private val api: UserApi) : BaseRepository() {

    suspend fun getUserInfo(
        token: String,
    ): ServiceResponse<User> {
        return try {
            api.getUserInfo(prepareToken(token))
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun changePassword(
        data: UserVM,
        token: String,
    ): ServiceResponse<User> {
        return try {
            api.changePassword(data, prepareToken(token))
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun login(
        data: UserVM,
    ): ServiceResponse<UserVM> {
        return try {
            api.login(data)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun register(
        data: UserVM,
    ): ServiceResponse<User> {
        return try {
            api.register(data)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }

    suspend fun update(
        token: String,
        data: UserVM,
    ): ServiceResponse<User> {
        return try {
            api.update(prepareToken(token), data)
        } catch (e: Exception) {
            ServiceResponse(status = "EXCEPTION", message = e.message)
        }
    }
}