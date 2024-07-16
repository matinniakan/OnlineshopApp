package com.example.onlineshopapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.onlineshopapp.ui.components.LoadingInColumn
import com.example.onlineshopapp.ui.components.products.ProductCategoryListView
import com.example.onlineshopapp.ui.components.products.ProductFilterView
import com.example.onlineshopapp.ui.components.products.ProductListItemView
import com.example.onlineshopapp.ui.components.sliders.SliderListView
import com.example.onlineshopapp.viewmodels.products.ProductViewModel


@Composable
fun HomeScreen(navController: NavHostController, viewModel: ProductViewModel = hiltViewModel()) {
    var productList by remember { mutableStateOf(viewModel.dataList) }
    var isLoading by remember { mutableStateOf(viewModel.isLoading) }

    val animatedVisibleState = remember { MutableTransitionState(false) }
        .apply { targetState = true }

    LazyColumn(modifier = Modifier.padding(20.dp, 0.dp)) {
        item {
            AnimatedVisibility(
                visibleState = animatedVisibleState,
                enter = slideInVertically(
                    animationSpec = tween(500, 1000),
                    initialOffsetY = { -40 }) + fadeIn(tween(500, 1000)),
                exit = fadeOut()
            ) {
                SliderListView()
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            AnimatedVisibility(
                visibleState = animatedVisibleState,
                enter = slideInVertically(
                    animationSpec = tween(500,1500),
                    initialOffsetY = { -40 }) + fadeIn(tween(500,1500)),
                exit = fadeOut()
            ) {
                ProductCategoryListView(navController = navController)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            AnimatedVisibility(
                visibleState = animatedVisibleState,
                enter = slideInVertically(
                    animationSpec = tween(500,2000),
                    initialOffsetY = { -40 }) + fadeIn(tween(500,2000)),
                exit = fadeOut()
            ) {
                ProductFilterView()
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        if (isLoading.value) {
            item {
                LoadingInColumn(
                    Modifier
                        .fillMaxSize()
                        .height(200.dp)
                )
            }
        } else {

            items(productList.value.size) { index ->
                AnimatedVisibility(
                    visibleState = animatedVisibleState,
                    enter = slideInVertically(
                        animationSpec = tween(500,2500),
                        initialOffsetY = { -40 }) + fadeIn(tween(500,2500)),
                    exit = fadeOut()
                ) {
                    ProductListItemView(productList.value[index], navController)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

