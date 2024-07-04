package com.unical.amazing.view.admin

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.viewmodel.admin.users.UserViewModel
import com.unical.amazing.swagger.models.OrderDto
import com.unical.amazing.viewmodel.admin.users.SharedUserViewModel
import com.unical.amazing.viewmodel.factories.UserManagementViewModelFactory

@Composable
fun OrderListView(
    context: Context,
    sharedUserViewModel : SharedUserViewModel
) {
    val viewModelFactory = remember { UserManagementViewModelFactory(context) }
    val vm: UserViewModel = viewModel(factory = viewModelFactory)
    vm.fetchUserOrders(sharedUserViewModel.selectedUser?.id.toString())
    val orders by vm.userorders.collectAsState()

    var selectedOrder by remember { mutableStateOf<OrderDto?>(null) }

    if (selectedOrder != null) {
        OrderDetailsView(
            order = selectedOrder!!,
            onBack = { selectedOrder = null }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(orders ?: emptyList()) { order ->
                OrderItemView(
                    order = order,
                    onDelete = { vm.deleteUserOrder(it.id) },
                    onClick = { selectedOrder = it }
                )
            }
        }
    }
}

@Composable
fun OrderItemView(
    order: OrderDto,
    onDelete: (OrderDto) -> Unit,
    onClick: (OrderDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(order) }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Ordine #${order.id}", style = MaterialTheme.typography.titleMedium)
            }
            IconButton(onClick = { onDelete(order) }) {
                Icon(Icons.Default.Delete, contentDescription = "Elimina ordine")
            }
        }
    }
}

@Composable
fun OrderDetailsView(
    order: OrderDto,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Dettagli Ordine", style = MaterialTheme.typography.titleLarge)
        Text("ID: ${order.id}")
        Text("Prodotti:")
        // Mostra i prodotti nell'ordine
        order.products.forEach { product ->
            Text("- ${product.product.title} (${product.quantity}x)")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Indietro")
        }
    }
}
