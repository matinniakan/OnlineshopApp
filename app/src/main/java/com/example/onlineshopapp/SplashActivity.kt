package com.example.onlineshopapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshopapp.db.viewmodels.UserEntityViewModel
import com.example.onlineshopapp.ui.screens.SplashScreen
import com.example.onlineshopapp.ui.theme.OnlineShopAppTheme
import com.example.onlineshopapp.utils.ThisApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLoad by remember { mutableStateOf(false) }
            val userEntityViewModel =
                ViewModelProvider(this).get(UserEntityViewModel::class.java)
            userEntityViewModel.getCurrentUser().observe(this) {
                userEntityViewModel.currentUser.value = it
                isLoad = true
            }
            OnlineShopAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (userEntityViewModel.currentUser.value != null) {
                        ThisApp.token = userEntityViewModel.currentUser.value!!.token!!
                    }
                    if(isLoad){
                        SplashScreen(splashActivity = this, userEntityViewModel = userEntityViewModel)
                    }

                }
            }
        }
    }
}