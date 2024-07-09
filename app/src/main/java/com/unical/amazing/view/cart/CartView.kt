package com.unical.amazing.view.cart

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.swagger.models.ProductWithQuantity
import com.unical.amazing.viewmodel.cart.CartManager
import com.unical.amazing.viewmodel.cart.CartViewModel
import com.unical.amazing.viewmodel.factories.CartViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartView(cartManager: CartManager, context: Context) {
    val cartItems by cartManager.getCartItems().collectAsStateWithLifecycle(emptyList())
    val viewModelFactory = remember { CartViewModelFactory(context) }
    val cartVM: CartViewModel = viewModel(factory = viewModelFactory)
    val checkoutResult by cartVM.checkoutResult.collectAsStateWithLifecycle(null)
    var showCheckoutMessage by remember { mutableStateOf(false) }

    LaunchedEffect(checkoutResult) {
        if (checkoutResult != null) {
            showCheckoutMessage = true
            if (checkoutResult == true) {
                delay(2000) // Attendere 2 secondi prima di svuotare il carrello
                cartManager.clearCart()
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CartViewModel") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartView()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxWidth()
                    ) {
                        CartItemList(cartItems, cartManager)
                    }
                    Box(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxWidth()
                    ) {
                        OrderSummary(
                            cartItems,
                            onCheckout = {
                                cartVM.checkout(cartItems)
                            },
                            checkoutResult = checkoutResult,
                            showCheckoutMessage = showCheckoutMessage
                        )
                    }
                }
            }
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
fun CartItemList(cartItems: List<ProductWithQuantity>, cartManager: CartManager) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(cartItems) { cartItem ->
            CartItemRow(cartItem, cartManager)
            Divider()
        }
    }
}

@Composable
fun CartItemRow(cartItem: ProductWithQuantity, cartManager: CartManager) {
    var quantity by remember { mutableStateOf(cartItem.quantity) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        cartItem.product.title?.let { Text(it) }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (quantity > 1) {
                    quantity -= 1
                    coroutineScope.launch {
                        cartManager.updateItemQuantity(cartItem, quantity)
                        println("Decreased quantity: ${cartItem.product.title} to $quantity")
                    }
                }
            }) {
                Icon(Icons.Default.Remove, contentDescription = null)
            }
            Text("$quantity")
            IconButton(onClick = {
                quantity += 1
                coroutineScope.launch {
                    cartManager.updateItemQuantity(cartItem, quantity)
                    println("Increased quantity: ${cartItem.product.title} to $quantity")
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
        Text("€${"%.2f".format(cartItem.product.price * quantity)}")
        IconButton(onClick = {
            coroutineScope.launch {
                cartManager.removeItemFromCart(cartItem)
                println("Removed from cart: ${cartItem.product.title}")
            }
        }) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
}

@Composable
fun OrderSummary(
    cartItems: List<ProductWithQuantity>,
    onCheckout: () -> Unit,
    checkoutResult: Boolean?,
    showCheckoutMessage: Boolean
) {
    val total = cartItems.sumOf { it.product.price * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total: €${"%.2f".format(total)}",
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (showCheckoutMessage) {
            if (checkoutResult == true) {
                Text("Ordine confermato", color = MaterialTheme.colors.primary)
            } else {
                Text("Errore, riprova", color = MaterialTheme.colors.error)
            }
        } else {
            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Checkout")
            }
        }
    }
}
