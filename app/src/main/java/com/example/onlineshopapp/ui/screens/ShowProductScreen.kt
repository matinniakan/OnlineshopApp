package com.example.onlineshopapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.onlineshopapp.db.models.BasketEntity
import com.example.onlineshopapp.db.viewmodels.BasketEntityViewModel
import com.example.onlineshopapp.ui.components.LoadingInColumn
import com.example.onlineshopapp.ui.theme.Dark
import com.example.onlineshopapp.ui.theme.Pink40
import com.example.onlineshopapp.ui.theme.Pink80
import com.example.onlineshopapp.ui.theme.Purple40
import com.example.onlineshopapp.ui.theme.Purple80
import com.example.onlineshopapp.ui.theme.PurpleGrey40
import com.example.onlineshopapp.ui.theme.PurpleGrey80
import com.example.onlineshopapp.ui.theme.Red
import com.example.onlineshopapp.viewmodels.products.ProductViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowProductScreen(
    productId: Long,
    navController: NavHostController,
    basketViewModel:BasketEntityViewModel,
    viewModel:ProductViewModel= hiltViewModel()
) {
    var data by remember { mutableStateOf(viewModel.data) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedSize by remember { mutableStateOf(0) }
    var selectedColor by remember { mutableStateOf(0) }

    val animatedVisibleState = remember { MutableTransitionState(false) }
        .apply { targetState = true }

    val context = LocalContext.current

    viewModel.getProductById(productId) { response ->
        isLoading = false
        if (response.status == "OK") {
            if (response.data!!.isNotEmpty()) {
                viewModel.data.value = response.data!![0]
            } else {
                Toast.makeText(context, "error on load data!!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }

    if (isLoading) {
        LoadingInColumn(modifier = Modifier.fillMaxSize())
    } else {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(0.dp)
        ) {
            Box {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = { data.value!!.image!! },
                    loading = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    failure = {
                        Text(text = "image request failed.")
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(5.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 500),
                            initialOffsetY = { -40 }
                        ) + fadeIn(
                            animationSpec = tween(500, 500)
                        ),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = " ",
                                Modifier.width(200.dp).height(200.dp)
                                    .background(color = Color.White).padding(7.dp),
                                tint = Color.Black,
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column() {
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 500),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 500)
                            ),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = data.value!!.title!!,
                                color = Color.White,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 800),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 800)
                            ),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = "${data.value!!.price!!}T",
                                color = Color.White,
                                fontSize = 26.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 1300),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 1300)
                            ),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = "Sizes",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 1500),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 1500)
                            ),
                            exit = fadeOut()
                        ) {
                            LazyRow {
                                items(data.value!!.sizes!!.size) { index ->
                                    androidx.compose.material.TextButton(
                                        onClick = { selectedSize = index },
                                        shape = RoundedCornerShape(15.dp),
                                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                            backgroundColor =
                                            if (selectedSize == index) Color.White else Color.Transparent
                                        ),
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Text(
                                            text = data.value!!.sizes!![index].title!!,
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedSize == index) Color.Black else Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 2000),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 2000)
                            ),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = "Colors",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 2200),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 2200)
                            ),
                            exit = fadeOut()
                        ) {
                            LazyRow {
                                items(data.value!!.colors!!.size) { index ->
                                    androidx.compose.material.TextButton(
                                        onClick = { selectedColor = index },
                                        shape = RoundedCornerShape(50.dp),
                                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                            backgroundColor =
                                            Color(android.graphics.Color.parseColor("#${data.value!!.colors!![index].hexValue}"))
                                        ),
                                        modifier = Modifier.size(40.dp),
                                        border = BorderStroke(1.dp, Color.White)
                                    ) {
                                        if (selectedColor == index) {
                                            androidx.compose.material.Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = "",
                                                tint = if (data.value!!.colors!![index].hexValue!!.lowercase(
                                                        Locale.ROOT
                                                    ) == "ffffff"
                                                ) Color.Black else Color.White
                                            )
                                        }

                                    }
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        androidx.compose.animation.AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 2700),
                                initialOffsetY = { -40 }
                            ) + fadeIn(
                                animationSpec = tween(500, 2700)
                            ),
                            exit = fadeOut()
                        ) {
                            androidx.compose.material.Button(
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val basket = BasketEntity(
                                            productId = productId,
                                            quantity = 1,
                                            sizeId = data!!.value!!.sizes!![selectedSize].id!!,
                                            colorId = data!!.value!!.colors!![selectedColor].id!!,
                                            image = data.value!!.image,
                                            price = data.value!!.price,
                                            title = data.value!!.title,
                                            colorHex = data.value!!.colors?.get(selectedColor)!!.hexValue!!,
                                            size = data!!.value!!.sizes!![selectedSize].title!!
                                        )
                                        basketViewModel.addToBasket(basket)
                                    }
                                    Toast.makeText(
                                        context,
                                        "product added to your basket",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.popBackStack()
                                },
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White
                                )
                            ) {
                                Text(
                                    text = "buy now",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                }

            }

        }

    }
}