package com.unical.amazing.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.swagger.models.ProductWithQuantity
import com.unical.amazing.viewmodel.cart.CartManager
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailView(product: ProductDto, cartManager: CartManager) {
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${product.title}") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                product.img_url?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable { showDialog = true },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (showDialog) {
                        FullScreenImageDialog(imageUrl = imageUrl) {
                            showDialog = false
                        }
                    }
                }

                product.title?.let {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                product.price.let {
                    Text(
                        text = "€ $it",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                product.description?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            if(product.available==true){
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val prod = ProductDto(product.id, product.title, product.price, product.description, true, product.img_url)
                            cartManager.addItemToCart(ProductWithQuantity(prod, 1))
                            println("Added to cart: ${product.title}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Aggiungi al carrello")
                }
            }
                else{
                Text(text = "Non disponibile",
                     color = Color.Red)
                }
            }
        }
    )
}









@Composable
fun FullScreenImageDialog(imageUrl: String, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable(onClick = onDismissRequest)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onDismissRequest),
                contentScale = ContentScale.Fit
            )
        }
    }
}
