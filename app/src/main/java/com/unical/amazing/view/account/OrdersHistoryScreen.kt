import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.swagger.models.OrderDto
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.viewmodel.account.OrdersHistoryViewModel
import com.unical.amazing.viewmodel.account.OrdersHistoryViewModelFactory

enum class Screen {
    OrdersList,
    OrderDetails
}

@Composable
fun OrdersHistoryScreen(context: Context) {
    var currentScreen by remember { mutableStateOf(Screen.OrdersList) }
    var selectedOrder by remember { mutableStateOf<OrderDto?>(null) }

    when (currentScreen) {
        Screen.OrdersList -> OrdersListView(
            context = context,
            onOrderClick = { order ->
                selectedOrder = order
                currentScreen = Screen.OrderDetails
            }
        )
        Screen.OrderDetails -> selectedOrder?.let { order ->
            OrderDetailsView(order = order, onBackClick = { currentScreen = Screen.OrdersList })
        }
    }
}

@Composable
fun OrdersListView(context: Context, onOrderClick: (OrderDto) -> Unit) {
    val viewModelFactory = remember { OrdersHistoryViewModelFactory(context) }
    val viewModel: OrdersHistoryViewModel = viewModel(factory = viewModelFactory)
    val ordersState = viewModel.orders.collectAsState()

    ordersState.value?.let { orderList ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Lista Ordini", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))
            orderList.forEach { order ->
                OrderSummaryView(order = order, onClick = { onOrderClick(order) })
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
        }
    } ?: run {
        Text("Caricamento in corso...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun OrderSummaryView(order: OrderDto, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID ordine: ${order.id}", style = MaterialTheme.typography.body1)
            val total = order.products.sumOf { it.product.price * it.quantity }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Totale: €${total}", style = MaterialTheme.typography.body1, color = Color.Black)
        }
    }
}


@Composable
fun OrderDetailsView(order: OrderDto, onBackClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextButton(onClick = onBackClick) {
            Text("Torna alla lista ordini")
        }
        Text("Dettagli ordine", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))
        order.products.forEach { productWithQuantity ->
            ProductDetailView(product = productWithQuantity.product, quantity = productWithQuantity.quantity)
        }
        val total = order.products.sumOf { it.product.price * it.quantity }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Totale: €${total}", style = MaterialTheme.typography.body1, color = Color.Black)
    }
}


@Composable
fun ProductDetailView(product: ProductDto, quantity: Int) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Nome: ${product.title}", style = MaterialTheme.typography.body1)
        Text(text = "Prezzo: €${product.price}", style = MaterialTheme.typography.body2, color = Color.Gray)
        Text(text = "Descrizione: ${product.description}", style = MaterialTheme.typography.body2, color = Color.Gray)
        Text(text = "Quantitá: $quantity", style = MaterialTheme.typography.body2, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

