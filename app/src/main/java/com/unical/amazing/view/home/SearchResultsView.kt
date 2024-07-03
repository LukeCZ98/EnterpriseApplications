package com.unical.amazing.view.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.unical.amazing.viewmodel.account.WishlistViewModel
import com.unical.amazing.viewmodel.factories.WishlistViewModelFactory
import com.unical.amazing.viewmodel.home.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchResultsView(viewModel: HomeViewModel, navController: NavController, query: String,context:Context) {
    // Avvia la ricerca quando la schermata viene mostrata
    viewModel.searchProducts(query)

    val productsList = viewModel.productsList
    val viewModelFactory = remember { WishlistViewModelFactory(context) }
    val wishlistViewModel: WishlistViewModel = viewModel(factory = viewModelFactory)
    val wishlists by wishlistViewModel.wishlists.collectAsState()
    Scaffold(
        topBar = { /* Add a top bar if needed */ },
        content = { paddingValues ->
            wishlists?.let {
                ProductList(
                    products = productsList,
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    it,
                    wishlistViewModel
                )
            }
        }
    )
}
