package com.unical.amazing.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unical.amazing.swagger.models.ProductDto
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.unical.amazing.viewmodel.home.HomeViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.unical.amazing.swagger.models.WishlistDto
import com.unical.amazing.viewmodel.account.WishlistViewModel
import com.unical.amazing.viewmodel.factories.WishlistViewModelFactory


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(viewModel: HomeViewModel, navController: NavController,context:Context) {
    val viewModelFactory = remember { WishlistViewModelFactory(context) }
    val wishlistViewModel: WishlistViewModel = viewModel(factory = viewModelFactory)
    val wishlists by wishlistViewModel.wishlists.collectAsState()

    Scaffold(
        topBar = { SearchBar(navController = navController) },
        content = { paddingValues ->
            wishlists?.let {
                ProductList(
                    products = viewModel.productList,
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    it,
                    wishlistViewModel
                )
            }
        }
    )
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavController) {
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
                        singleLine = true
                    )
                }
                IconButton(
                    onClick = { navController.navigate("searchResults/$text") },
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
    navController: NavController,
    modifier: Modifier = Modifier,
    wishlists: List<WishlistDto?>,
    wishlistViewModel: WishlistViewModel
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
                    ProductItem(product, navController,wishlists,wishlistViewModel)
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
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







@Composable
fun ProductItem(product: ProductDto, navController: NavController,wishl: List<WishlistDto?>,wishlistViewModel: WishlistViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedWishlist by remember { mutableStateOf<String?>(null) }
    val wishlists by wishlistViewModel.wishlists.collectAsState() // Example wishlist names
    var showToast by remember { mutableStateOf(false) }
    // Dummy check for product already in wishlist
    // This should be replaced with actual logic to check if the product is in any wishlist


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("productDetail/${product.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6200EE),
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            product.img_url?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = product.title,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            product.title?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            product.price.let {
                Text(
                    text = "€ $it",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Add to Wishlist")
                }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Select Wishlist") },
            text = {
                Column {
                    wishlists?.forEach { wishlist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedWishlist = wishlist.name
                                    val productAdded = addProductToWishlist(product, wishlist.name,wishl,wishlistViewModel)
                                    if(!productAdded)
                                        showToast = true
                                    showDialog = false
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = wishlist.name)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    val context = LocalContext.current
    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Prodotto giá esistente nella wishlist!", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }
}

fun addProductToWishlist(product: ProductDto, wishlist: String,wishlists: List<WishlistDto?>,wishlistViewModel: WishlistViewModel):Boolean {
    for(Wishlist in wishlists){
        if (Wishlist != null) {
            if (Wishlist.name == wishlist) {
                if(Wishlist.items?.contains(product) == false){
                    product.id?.let { wishlistViewModel.addProductToWishlist(it, Wishlist.id) }
                    return true
                }
            }
        }
    }
    return false
}