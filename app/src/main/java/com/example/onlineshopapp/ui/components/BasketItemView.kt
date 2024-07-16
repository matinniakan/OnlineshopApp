package com.example.onlineshopapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.onlineshopapp.db.models.BasketEntity
import com.example.onlineshopapp.db.viewmodels.BasketEntityViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketItemView(
    basketEntity: BasketEntity,
    viewModel: BasketEntityViewModel,
    totalPrice: MutableState<Long>,
    navController: NavHostController
) {

    var quantity by remember { mutableStateOf(basketEntity.quantity) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
    ) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true
                ),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                navController.navigate("showProduct/${basketEntity.productId}")
            }
        ) {
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { basketEntity.image },
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
                    androidx.compose.material.Text(text = "image request failed.")
                }
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            androidx.compose.material.Text(
                text = basketEntity.title!!,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(5.dp))
            androidx.compose.material.Text(
                text = "${basketEntity.price}T",
                fontWeight = FontWeight.Light,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                androidx.compose.material.IconButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.decrementQuantity(basketEntity)
                    }
                    quantity--
                    totalPrice.value -= basketEntity.price!!
                }, Modifier.size(25.dp)) {
                    androidx.compose.material.Icon(
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                androidx.compose.material.Text(
                    text = quantity.toString(),
                    fontSize = 20.sp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black)
                Spacer(modifier = Modifier.width(10.dp))
                androidx.compose.material.IconButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.incrementQuantity(basketEntity)
                    }
                    quantity++
                    totalPrice.value += basketEntity.price!!
                }, Modifier.size(25.dp)) {
                    androidx.compose.material.Icon(
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                androidx.compose.material.IconButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.delete(basketEntity)
                    }
                }, Modifier.size(25.dp)) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",
                        tint = Color.Red
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(android.graphics.Color.parseColor("#${basketEntity.colorHex}"))

            ),
            modifier = Modifier.size(40.dp),
            border = BorderStroke(1.dp, Color.White),
            content={}
        )
        Spacer(modifier = Modifier.width(10.dp))
        androidx.compose.material.Text(
            text = basketEntity.size,
            fontSize = 22.sp,
            color = Color.Gray
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

