package com.example.onlineshopapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavHostController
import com.example.onlineshopapp.MainActivity
import com.example.onlineshopapp.db.viewmodels.BasketEntityViewModel
import com.example.onlineshopapp.db.viewmodels.UserEntityViewModel
import com.example.onlineshopapp.models.customer.UserVM
import com.example.onlineshopapp.ui.theme.Dark
import com.example.onlineshopapp.ui.theme.DarkBlue
import com.example.onlineshopapp.viewmodels.customers.UserViewModel
import com.example.onlineshopapp.viewmodels.invoices.TransactionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ChangePasswordScreen(
    navController: NavHostController,
    userEntityViewModel: UserEntityViewModel,
    userViewModel: UserViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var oldPassword by remember { mutableStateOf(TextFieldValue()) }
    var oldPasswordError by remember { mutableStateOf(false) }

    var newPassword by remember { mutableStateOf(TextFieldValue()) }
    var newPasswordError by remember { mutableStateOf(false) }

    var repeatPassword by remember { mutableStateOf(TextFieldValue()) }
    var repeatPasswordError by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
        item {
            Column(
                Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Text(text = "Changing your Password", fontSize = 22.sp)
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = oldPassword, onValueChange = {
                        oldPassword = it
                        oldPasswordError = false
                    },
                    label = { Text(text = "Old Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        if (oldPasswordError) {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "error",
                                tint = Color.Red
                            )
                        }
                    },
                    isError = oldPasswordError
                )
                if (oldPasswordError) {
                    Text(
                        text = "Please Enter Your Old Password!",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = newPassword, onValueChange = {
                        newPassword = it
                        newPasswordError = false
                    },
                    label = { Text(text = "New Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        if (newPasswordError) {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "error",
                                tint = Color.Red
                            )
                        }
                    },
                    isError = newPasswordError
                )
                if (newPasswordError) {
                    Text(
                        text = "Please Enter Your New Password!",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = repeatPassword, onValueChange = {
                        repeatPassword = it
                        repeatPasswordError = false
                    },
                    label = { Text(text = " Repeat Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        if (repeatPasswordError) {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "error",
                                tint = Color.Red
                            )
                        }
                    },
                    isError = repeatPasswordError
                )
                if (repeatPasswordError) {
                    Text(
                        text = "Please Repeat Your Password!",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))
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
                } else {
                    Column {
                        Button(
                            onClick = {
                                if (oldPassword.text.isEmpty()) {
                                    oldPasswordError = true
                                }
                                if (newPassword.text.isEmpty()) {
                                    newPasswordError = true
                                }
                                if (repeatPassword.text.isEmpty()) {
                                    repeatPasswordError = true
                                }
                                if (oldPasswordError || newPasswordError || repeatPasswordError) return@Button

                                if (newPassword.text != repeatPassword.text) {
                                    repeatPasswordError = true
                                    return@Button
                                }
                                isLoading = true
                                val userId = userEntityViewModel.currentUser.value!!.userId
                                CoroutineScope(Dispatchers.IO).launch {
                                    val isOldPasswordCorrect = userEntityViewModel.checkOldPassword(userId, oldPassword.text)
                                    if (isOldPasswordCorrect) {
                                        val isPasswordUpdated = userEntityViewModel.replacePassword(userId, newPassword.text)
                                        if (isPasswordUpdated) {
                                            CoroutineScope(Dispatchers.IO).launch {
                                                userViewModel.changePassword(
                                                    UserVM(
                                                        oldPassword = oldPassword.text,
                                                        password = newPassword.text,
                                                        repeatPassword = repeatPassword.text,
                                                        //token = userViewModel.token
                                                    )
                                                ) { response ->
                                                    isLoading = false
                                                    if (response.status =="OK" && response.data!!.isNotEmpty()) {
                                                        val user = response.data!![0]
                                                        CoroutineScope(Dispatchers.IO).launch {
                                                            userEntityViewModel.update(user.convertToEntity())
                                                        }
                                                        Toast.makeText(
                                                            context,
                                                            "Password changed successfully",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        navController.navigate("home")
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            "Failed to change password: ${response.message}",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            }
                                        } else {
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(
                                                    context,
                                                    "Failed to update password in the database",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                isLoading = false
                                            }
                                        }
                                    } else {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                "Old password is incorrect",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            isLoading = false
                                        }
                                    }
                                }
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Dark
                            )
                        ) {
                            Text(
                                text = "Save Changes",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}