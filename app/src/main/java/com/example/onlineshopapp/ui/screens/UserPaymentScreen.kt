package com.example.onlineshopapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.onlineshopapp.MainActivity
import com.example.onlineshopapp.db.viewmodels.BasketEntityViewModel
import com.example.onlineshopapp.db.viewmodels.UserEntityViewModel
import com.example.onlineshopapp.models.customer.UserVM
import com.example.onlineshopapp.models.invoices.InvoiceItem
import com.example.onlineshopapp.models.invoices.PaymentTransaction
import com.example.onlineshopapp.ui.theme.Dark
import com.example.onlineshopapp.viewmodels.customers.UserViewModel
import com.example.onlineshopapp.viewmodels.invoices.TransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun UserPaymentScreen(
    mainActivity: MainActivity,
    navController: NavController,
    basketViewModel: BasketEntityViewModel,
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    userEntityViewModel: UserEntityViewModel
) {
    val context = LocalContext.current
    val currentUser by remember { mutableStateOf(userEntityViewModel.currentUser) }
    val isLoggedIn by remember { mutableStateOf(userEntityViewModel.currentUser.value != null) }

    var firstName by remember { mutableStateOf(TextFieldValue(if (isLoggedIn) currentUser.value!!.firstName!! else "")) }
    var firstNameError by remember { mutableStateOf(false) }

    var lastName by remember { mutableStateOf(TextFieldValue(if (isLoggedIn) currentUser.value!!.lastName!! else "")) }
    var lastNameError by remember { mutableStateOf(false) }

    var phone by remember { mutableStateOf(TextFieldValue(if (isLoggedIn) currentUser.value!!.phone!! else "")) }
    var phoneError by remember { mutableStateOf(false) }

    var address by remember { mutableStateOf(TextFieldValue(if (isLoggedIn) currentUser.value!!.address!! else "")) }
    var addressError by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf(TextFieldValue(if (isLoggedIn) currentUser.value!!.username!! else "")) }
    var usernameError by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf(TextFieldValue()) }
    var passwordError by remember { mutableStateOf(false) }

    var postalCode by remember { mutableStateOf(TextFieldValue(if (isLoggedIn) currentUser.value!!.postalCode!! else "")) }
    var postalCodeError by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    var focusManager = LocalFocusManager.current

    val animatedVisibleState = remember { MutableTransitionState(false) }
        .apply { targetState = true }

    Column {
        AnimatedVisibility(
            visibleState = animatedVisibleState,
            enter = slideInVertically(
                animationSpec = tween(500, 500),
                initialOffsetY = { -40 }) + fadeIn(tween(500, 500)),
            exit = fadeOut()
        ) {
            Row {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
                Text(
                    text = "Complete Your Information",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                )
            }
        }
        LazyColumn {
            item {
                Column(Modifier.padding(20.dp)) {
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 700),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 700)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = firstName, onValueChange = {
                                firstName = it
                                firstNameError = false
                            },
                            label = { Text(text = "First Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            trailingIcon = {
                                if (firstNameError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = firstNameError
                        )
                        if (firstNameError) {
                            Text(
                                text = "Please Enter Your First Name!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 900),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 900)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = lastName, onValueChange = {
                                lastName = it
                                lastNameError = false
                            },
                            label = { Text(text = "Last Name") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            singleLine = true,
                            trailingIcon = {
                                if (lastNameError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = lastNameError
                        )
                        if (lastNameError) {
                            Text(
                                text = "Please Enter Your Last Name!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 1100),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 1100)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = phone, onValueChange = {
                                phone = it
                                phoneError = false
                            },
                            label = { Text(text = "Phone") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            trailingIcon = {
                                if (phoneError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = phoneError
                        )
                        if (phoneError) {
                            Text(
                                text = "Please Enter Your Phone!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 1300),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 1300)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = username, onValueChange = {
                                username = it
                                usernameError = false
                            },
                            label = { Text(text = "User Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            enabled = currentUser.value == null,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            trailingIcon = {
                                if (usernameError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = usernameError
                        )
                        if (usernameError) {
                            Text(
                                text = "Please Enter Your User Name!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 1500),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 1500)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = password, onValueChange = {
                                password = it
                                passwordError = false
                            },
                            label = { Text(text = "Password") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            enabled = currentUser.value == null,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            trailingIcon = {
                                if (passwordError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = passwordError
                        )
                        if (passwordError) {
                            Text(
                                text = "Please Enter Your Password!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 1700),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 1700)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = postalCode, onValueChange = {
                                postalCode = it
                                postalCodeError = false
                            },
                            label = { Text(text = "Postal Code") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            trailingIcon = {
                                if (postalCodeError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = postalCodeError
                        )
                        if (postalCodeError) {
                            Text(
                                text = "Please Enter Your Postal Code!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AnimatedVisibility(
                        visibleState = animatedVisibleState,
                        enter = slideInVertically(
                            animationSpec = tween(500, 1900),
                            initialOffsetY = { -40 }) + fadeIn(tween(500, 1900)),
                        exit = fadeOut()
                    ) {
                        OutlinedTextField(
                            value = address, onValueChange = {
                                address = it
                                addressError = false
                            },
                            label = { Text(text = "Address") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                if (addressError) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = "error",
                                        tint = Color.Red
                                    )
                                }
                            },
                            isError = addressError
                        )
                        if (addressError) {
                            Text(
                                text = "Please Enter Your Address!",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    if (!isLoading) {
                        AnimatedVisibility(
                            visibleState = animatedVisibleState,
                            enter = slideInVertically(
                                animationSpec = tween(500, 2100),
                                initialOffsetY = { -40 }) + fadeIn(tween(500, 2100)),
                            exit = fadeOut()
                        ) {
                            Button(
                                onClick = {
                                    if (firstName.text.isEmpty()) {
                                        firstNameError = true
                                    }
                                    if (lastName.text.isEmpty()) {
                                        lastNameError = true
                                    }
                                    if (username.text.isEmpty()) {
                                        usernameError = true
                                    }
                                    if (phone.text.isEmpty()) {
                                        phoneError = true
                                    }
                                    if (currentUser.value == null &&
                                        password.text.isEmpty()
                                    ) {
                                        passwordError = true
                                    }
                                    if (address.text.isEmpty()) {
                                        addressError = true
                                    }
                                    if (postalCode.text.isEmpty()) {
                                        postalCodeError = true
                                    }
                                    if (firstNameError || lastNameError || usernameError || passwordError || phoneError || addressError || postalCodeError) {
                                        return@Button
                                    }
                                    var userInfo = UserVM(
                                        id = if (currentUser.value == null) null else currentUser.value!!.userId,
                                        customerId = if (currentUser.value == null) null else currentUser.value!!.customerId,
                                        username = username.text,
                                        password = password.text,
                                        firstName = firstName.text,
                                        lastName = lastName.text,
                                        phone = phone.text,
                                        address = address.text,
                                        postalCode = postalCode.text,
                                    )
                                    var items = ArrayList<InvoiceItem>()
                                    basketViewModel.dataList.value.forEach {
                                        items.add(InvoiceItem.convertFromBasket(it))
                                    }
                                    var request = PaymentTransaction(
                                        user = userInfo,
                                        items = items
                                    )
                                    isLoading = true
                                    transactionViewModel.goToPayment(request) { response ->
                                        if (response.status == "OK" && response.data!!.isNotEmpty()) {
                                            if (currentUser.value == null) {
                                                userViewModel.login(
                                                    UserVM(
                                                        username = username.text,
                                                        password = password.text
                                                    )
                                                ) { userResponse ->
                                                    if (userResponse.status == "OK") {
                                                        val user = userResponse.data!![0]
                                                        CoroutineScope(Dispatchers.IO).launch {
                                                            userEntityViewModel.insert(user.convertToEntity())
                                                        }
                                                    }
                                                }
                                            }
                                            CoroutineScope(Dispatchers.IO).launch {
                                                basketViewModel.deleteAll()
                                                mainActivity.finish()
                                            }
                                            val intent =
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(response.data!![0])
                                                )
                                            context.startActivity(intent)
                                        } else if (response.message!!.isNotEmpty()) {
                                            Toast.makeText(
                                                context,
                                                response.message!!,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        isLoading = false
                                    }
                                },
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Dark
                                ),
                                ) { if (currentUser.value == null){
                                Text(
                                    text = "Register & Pay",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }else{
                                Text(
                                    text = "Pay",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            }
                        }
                    }
                    if (isLoading) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}
