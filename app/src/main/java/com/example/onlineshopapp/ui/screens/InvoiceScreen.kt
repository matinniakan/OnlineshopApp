package com.example.onlineshopapp.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.onlineshopapp.ui.components.AdvanceButton
import com.example.onlineshopapp.ui.components.LoadingInColumn
import com.example.onlineshopapp.ui.theme.LightBlue
import com.example.onlineshopapp.ui.theme.Red
import com.example.onlineshopapp.viewmodels.invoices.InvoiceItemViewModel

@Composable
fun InvoiceScreen(
    navController: NavController,
    id: Long,
    viewModel: InvoiceItemViewModel = hiltViewModel()
) {
    var invoice by remember { mutableStateOf(viewModel.data) }
    var isLoading by remember { mutableStateOf(viewModel.isLoading) }

    if (isLoading.value || invoice.value == null) {
        LoadingInColumn(
            modifier = Modifier
                .fillMaxSize()
        )
    } else {
        Column() {
            Row {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(
                        text = "Invoice", textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(0.dp, 9.dp, 0.dp, 0.dp),
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.padding(5.dp),
                    shape = RoundedCornerShape(50.dp),
                    backgroundColor = if (invoice.value!!.status == "NotPayed") Red else
                        Color.Green
                ) {
                    Icon(
                        imageVector = if (invoice.value!!.status == "NotPayed") Icons.Filled.Close
                        else Icons.Filled.Check,
                        contentDescription = "",
                        Modifier
                            .size(200.dp)
                            .padding(30.dp),
                        tint = Color.White
                    )
                }
            }
            Card( modifier = Modifier
                .background(Color.Transparent)
                .padding(40.dp, 0.dp),
                elevation = 3.dp,
                shape = RoundedCornerShape(25.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(text = "Status : ${invoice.value!!.status!!}", fontSize = 21.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Add Date : ${invoice.value!!.addDate!!}", fontSize = 21.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    if (!invoice.value!!.paymentDate.isNullOrEmpty()) {
                        Text(
                            text = "Payment Date : ${invoice.value!!.paymentDate!!}",
                            fontSize = 21.sp
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }
}