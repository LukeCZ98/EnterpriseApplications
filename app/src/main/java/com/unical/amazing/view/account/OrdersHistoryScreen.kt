package com.unical.amazing.view.account

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.swagger.models.OrderDto
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.viewmodel.account.OrdersHistoryViewModel
import com.unical.amazing.viewmodel.account.OrdersHistoryViewModelFactory

@Composable
fun OrdersHistoryScreen(context: Context) {
    val viewModelFactory = remember { OrdersHistoryViewModelFactory(context) }
    val viewModel: OrdersHistoryViewModel = viewModel(factory = viewModelFactory)
    val ordersState = viewModel.orders.collectAsState()

    ordersState.value?.let { orderList ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Orders History", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))
            orderList.forEach { order ->
                val total = order.product.sumOf { it.price * order.quantity }
                OrderItemView(order.id, order.product, order.quantity, total)
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun OrderItemView(orderId: Int, products: List<ProductDto>, quantity: Int, total: Double) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            products.forEach { product ->
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = "Nome: ${product.title}", style = MaterialTheme.typography.body1)
                    Text(text = "Prezzo: $${product.price}", style = MaterialTheme.typography.body2, color = Color.Gray)
                    Text(text = "Quantitá: $quantity", style = MaterialTheme.typography.body2, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Totale: €${total}", style = MaterialTheme.typography.body1, color = Color.Black)
        }
    }
}
