package com.example.onlineshopapp.viewmodels.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshopapp.models.ServiceResponse
import com.example.onlineshopapp.models.customer.User
import com.example.onlineshopapp.models.customer.UserVM
import com.example.onlineshopapp.repositories.customer.UserRepository
import com.example.onlineshopapp.utils.ThisApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    var token: String = ThisApp.token

    fun getUserInfo(onResponse: (response: ServiceResponse<User>) -> Unit) {
        viewModelScope.launch {
            val response = repository.getUserInfo(token)
            onResponse(response)
        }
    }

    fun changePassword(data: UserVM, onResponse: (response: ServiceResponse<User>) -> Unit) {
        viewModelScope.launch {
            val response = repository.changePassword(data, token)
            onResponse(response)
        }
    }

    fun login(data: UserVM, onResponse: (response: ServiceResponse<UserVM>) -> Unit) {
        viewModelScope.launch {
            val response = repository.login(data)
            onResponse(response)
        }
    }

    fun register(data: UserVM, onResponse: (response: ServiceResponse<User>) -> Unit) {
        viewModelScope.launch {
            val response = repository.register(data)
            onResponse(response)
        }
    }

    fun update(data: UserVM, onResponse: (response: ServiceResponse<User>) -> Unit) {
        viewModelScope.launch {
            val response = repository.update(token, data)
            onResponse(response)
        }
    }
}