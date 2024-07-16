package com.example.onlineshopapp.models.invoices

import com.example.onlineshopapp.models.customer.UserVM


data class PaymentTransaction(
    var items: List<InvoiceItem>,
    var user: UserVM,
)
