package com.unical.amazing.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.swagger.apis.ProductApi
import com.unical.amazing.swagger.models.ProductDto
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeActivity() {
    val context = LocalContext.current
    val viewModel: ProductViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadProducts(context)
    }

    Scaffold(
        topBar = { SearchBar() }
    ) {
        ProductList(products = viewModel.productList)
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductList(products: List<ProductDto>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (products.isNotEmpty()) {
            Text(
                text = "Prodotti",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            products.forEach { product ->
                ProductItem(product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            Text(
                text = "Nessun prodotto trovato",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductItem(product: ProductDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            product.title?.let {
                Text(
                    text = it,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Prezzo: ${product.price}",
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Descrizione: ${product.description}",
            )
        }
    }
}

class ProductViewModel : ViewModel() {
    var productList by mutableStateOf(emptyList<ProductDto>())
        private set

    fun loadProducts(context: android.content.Context) {
        viewModelScope.launch {
            try {
                // Ottieni la lista dei prodotti dall'API
                productList = ProductApi().getAll().toList()
            } catch (e: Exception) {
                // Gestisci gli errori di connessione o altri problemi
                Toast.makeText(context, "Errore durante il recupero dei prodotti $e", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
