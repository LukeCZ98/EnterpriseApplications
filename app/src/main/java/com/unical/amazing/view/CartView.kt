package com.unical.amazing.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unical.amazing.model.CartItem




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartView() {
    val cartItems = remember { mutableStateListOf<CartItem>() } // Mock list of cart items
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") }
            )
        }
    ) {
        if (cartItems.isEmpty()) {
            EmptyCartView()
        } else {
            CartItemList(cartItems)
        }
    }
}


@Composable
fun EmptyCartView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Your cart is empty")
    }
}

@Composable
fun CartItemList(cartItems: List<CartItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(cartItems) { cartItem ->
            CartItemRow(cartItem)
            Divider()
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(cartItem.name)
        Text("Qty: ${cartItem.quantity}")
        Text("\$${cartItem.price}")
    }
}
