package com.unical.amazing.view.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.model.account.Order
import com.unical.amazing.viewmodel.account.AccountViewModel

@Composable
fun OrdersHistoryScreen(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()
    //TODO -> aggiornare con il rispettivo codice per leggere gli ordini
    userState.value?.let { user ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Orders History", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))
            user.orders?.forEach { order ->
                OrderItemView(order)
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun OrderItemView(order: Order) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: ${order.orderId}", style = MaterialTheme.typography.body1)
            Text(text = "Date: ${order.date}", style = MaterialTheme.typography.body2, color = Color.Gray)
            Text(text = "Status: ${order.status}", style = MaterialTheme.typography.body2, color = Color.Gray)
            order.items.forEach { item ->
                Text(text = "${item.name} x${item.quantity} - $${item.price}", style = MaterialTheme.typography.body2)
            }
        }
    }
}
