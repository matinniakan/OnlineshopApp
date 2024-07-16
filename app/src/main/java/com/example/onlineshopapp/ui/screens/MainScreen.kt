package com.example.onlineshopapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.onlineshopapp.MainActivity
import com.example.onlineshopapp.db.viewmodels.BasketEntityViewModel
import com.example.onlineshopapp.db.viewmodels.UserEntityViewModel
import com.example.onlineshopapp.ui.components.TopAppView
import com.example.onlineshopapp.utils.ThisApp

@Composable
fun MainScreen(mainActivity: MainActivity) {

    val navController = rememberNavController()
    var fullScreen by remember { mutableStateOf(false) }
    var showHomeButton by remember { mutableStateOf(false) }

    val basketViewModel =
        ViewModelProvider(mainActivity).get(BasketEntityViewModel::class.java)
    val userEntityViewModel =
        ViewModelProvider(mainActivity).get(UserEntityViewModel::class.java)
    basketViewModel.getAllBasketLive().observe(mainActivity) {
        basketViewModel.dataList.value = it
    }
    userEntityViewModel.getCurrentUser().observe(mainActivity) {
        userEntityViewModel.currentUser.value = it
    }
    Scaffold(
        topBar = {
            if (!fullScreen)
                TopAppView(navController, basketViewModel,userEntityViewModel,showHomeButton)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            startDestination = "home",
            navController = navController
        ) {
            composable("home") {
                showHomeButton = false
                fullScreen = false
                HomeScreen(navController)
            }
            composable("basket") {
                showHomeButton = false
                fullScreen = true
                BasketListScreen(navController, basketViewModel)
            }
            composable("proceedToPayment") {
                showHomeButton = false
                fullScreen = true
                UserPaymentScreen(
                    navController = navController,
                    basketViewModel = basketViewModel,
                    mainActivity = mainActivity,
                    userEntityViewModel = userEntityViewModel
                )
            }
            composable(
                "products/{categoryId}/{title}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.LongType },
                    navArgument("title") { type = NavType.StringType }
                )
            ) { backStack ->
                showHomeButton = true
                fullScreen = false
                val id = backStack.arguments?.getLong("categoryId")
                val title = backStack.arguments?.getString("title")
                ThisApp.productCategoryId = id!!
                ProductsScreen(id, title!!, navController)
            }
            composable(
                "showProduct/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.LongType })
            ) { backStack ->
                showHomeButton = false
                fullScreen = true
                backStack.arguments?.getLong("productId").let {
                    ShowProductScreen(it!!, navController, basketViewModel)
                }
            }
            composable(
                "invoice/{id}",
                deepLinks = listOf(navDeepLink { uriPattern = "app://onlineshopholosen.ir/{id}" }),
                arguments = listOf(
                    navArgument("id"){type= NavType.LongType}
                )
            ){ backStackEntry ->
                showHomeButton = true
                if (userEntityViewModel.currentUser.value != null){
                    ThisApp.invoiceId = backStackEntry.arguments?.getLong("id")!!
                    ThisApp.token = userEntityViewModel.currentUser.value!!.token!!
                }
                InvoiceScreen(navController, backStackEntry.arguments?.getLong("id")!!)
            }
            composable("login") {
                showHomeButton = false
                fullScreen = true
                LoginScreen(navController,userEntityViewModel)
            }
            composable("dashboard") {
                showHomeButton = false
                fullScreen = true
                DashboardScreen(navController,userEntityViewModel)
            }
            composable("invoices") {
                /*ThisApp.userId=userEntityViewModel.currentUser.value!!.userId
               ThisApp.token=userEntityViewModel.currentUser.value!!.token!!*/
                showHomeButton = false
                fullScreen = true
                InvoiceListScreen(navController)
            }
            composable("editProfile") {
                //ThisApp.token =userEntityViewModel.currentUser.value.token!!
                showHomeButton = false
                fullScreen = true
                EditProfileScreen(navController,userEntityViewModel)
            }
            composable("changePassword") {
                showHomeButton = false
                fullScreen = true
                ChangePasswordScreen(  navController,userEntityViewModel)
            }
            composable("register") {
                showHomeButton = false
                fullScreen = true
                RegisterScreen(
                    navController = navController,
                    userEntityViewModel = userEntityViewModel
                )
            }
        }
    }
}