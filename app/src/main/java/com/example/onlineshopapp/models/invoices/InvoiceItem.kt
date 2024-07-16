package com.example.onlineshopapp.models.invoices

import com.example.onlineshopapp.db.models.BasketEntity
import com.example.onlineshopapp.models.products.Product


data class InvoiceItem(
    var id: Long? = null,
    var product: Product?,
    var quantity: Int?,
    var unitPrice: Long? = 0,
) {
    companion object {
        fun convertFromBasket(basketEntity: BasketEntity): InvoiceItem {
            return InvoiceItem(
                id = null,
                product = Product(id = basketEntity.productId),
                quantity = basketEntity.quantity,
            )
        }
    }
}
