package com.unical.amazing.view.admin

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.viewmodel.admin.product.ProductViewModel
import com.unical.amazing.viewmodel.admin.product.SharedProductViewModel
import com.unical.amazing.viewmodel.factories.ProductManagementViewModelFactory

@SuppressLint("RememberReturnType")
@Composable
fun ProductManagementView(context: Context, adminNavController: NavHostController,sharedProductViewModel: SharedProductViewModel) {
    val viewModelFactory = remember { ProductManagementViewModelFactory(context) }
    val viewModel: ProductViewModel = viewModel(factory = viewModelFactory)
    val products by viewModel.prods.collectAsState()
    var productList by remember { mutableStateOf(products ?: emptyArray<ProductDto>()) }

    // Fetch data when the composable is first launched
    LaunchedEffect(key1 = true) {
        viewModel.fetchall()
    }

    // Update productList when products changes
    LaunchedEffect(products) {
        productList = products ?: emptyArray()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { adminNavController.navigate("addproduct") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aggiungi Prodotto")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(products ?: emptyArray()) { product ->
                ProductItem(
                    product = product,
                    viewModel = viewModel,
                    sharedProductViewModel = sharedProductViewModel,
                    adminNavController = adminNavController
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    product: ProductDto,
    viewModel: ProductViewModel,
    sharedProductViewModel: SharedProductViewModel,
    adminNavController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            product.title?.let { Text(text = it) }
            Text(text = "${product.price} €")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    sharedProductViewModel.selectedProduct = product
                    adminNavController.navigate("editproduct")
                }
            ) {
                Text("Modifica")
            }

            Button(
                onClick = {
                    viewModel.deleteProduct(product)
                }
            ) {
                Text("Elimina")
            }
        }
    }
}
