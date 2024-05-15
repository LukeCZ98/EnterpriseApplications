package com.unical.amazing.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.viewmodel.HomeViewModel
import com.unical.amazing.model.Product


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeActivity(){
    val viewModel: HomeViewModel = viewModel()
    val products by viewModel.products.collectAsState()

    Scaffold(
        topBar = { SearchBar() }
    ) {
        ProductList(products)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .heightIn(min = 36.dp)
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText -> text = newText },
                        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        singleLine = true,
                    )
                }
                IconButton(
                    onClick = { /* handle search click */ },
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            }
        },
        modifier = Modifier.background(Color.LightGray)
    )
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = product.title,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${product.price} €",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}
