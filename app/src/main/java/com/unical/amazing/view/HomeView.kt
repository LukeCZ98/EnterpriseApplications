package com.unical.amazing.view

import android.annotation.SuppressLint
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView() {
    val gfgPolicy = ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(gfgPolicy)

    val context = LocalContext.current
    val viewModel: ProductViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadProducts(context)
    }

    Scaffold(
        topBar = { SearchBar() },
        content = { paddingValues ->
            ProductList(
                products = viewModel.productList,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
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
fun ProductList(
    products: List<ProductDto>,
    modifier: Modifier = Modifier
) {
    val visible by remember { mutableStateOf(true) }

    if (products.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                AnimatedVisibility(
                    visible = visible,
                    enter = androidx.compose.animation.fadeIn(animationSpec = tween(durationMillis = 1000)),
                    exit = androidx.compose.animation.fadeOut(animationSpec = tween(durationMillis = 1000))
                ) {
                    ProductItem(product)
                }
            }
            // Aggiungi uno Spacer alla fine della lista per creare spazio
            item {
                Spacer(modifier = Modifier.height(80.dp)) // Adjust the height as needed
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color.Blue)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nessun prodotto trovato",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
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
        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6200EE), // Colore vivace, puoi cambiarlo a tuo piacimento
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            product.title?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Prezzo: ${product.price}",
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Descrizione: ${product.description}",
                fontSize = 14.sp,
                color = Color.White
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
