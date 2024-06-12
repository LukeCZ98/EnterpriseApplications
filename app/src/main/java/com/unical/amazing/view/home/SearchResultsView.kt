package com.unical.amazing.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.unical.amazing.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchResultsView(viewModel: HomeViewModel, navController: NavController, query: String) {
    // Avvia la ricerca quando la schermata viene mostrata
    viewModel.searchProducts(query)

    val productsList = viewModel.productsList

    Scaffold(
        topBar = { /* Add a top bar if needed */ },
        content = { paddingValues ->
            ProductList(
                products = productsList,
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}
