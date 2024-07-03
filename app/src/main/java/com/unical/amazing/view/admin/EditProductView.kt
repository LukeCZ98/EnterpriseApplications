package com.unical.amazing.view.admin

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.unical.amazing.viewmodel.admin.product.ProductViewModel
import com.unical.amazing.viewmodel.admin.product.SharedProductViewModel
import com.unical.amazing.viewmodel.factories.ProductManagementViewModelFactory

@SuppressLint("RememberReturnType")
@Composable
fun EditProductView(
    context: Context,
    adminNavController: NavHostController,
    sharedProductViewModel: SharedProductViewModel
) {
    val viewModelFactory = remember { ProductManagementViewModelFactory(context) }
    val vm: ProductViewModel = viewModel(factory = viewModelFactory)

    val prod = sharedProductViewModel.selectedProduct ?: return

    var title by remember { mutableStateOf(prod.title ?: "") }
    var price by remember { mutableStateOf(prod.price.toString()) }
    var description by remember { mutableStateOf(prod.description ?: "") }
    var available by remember { mutableStateOf(prod.available ?: false) }
    var imgUrl by remember { mutableStateOf(prod.img_url ?: "") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Modifica Prodotto", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titolo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Prezzo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrizione") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = available,
                onCheckedChange = { available = it }
            )
            Text("Disponibile")
        }

        OutlinedTextField(
            value = imgUrl,
            onValueChange = { imgUrl = it },
            label = { Text("URL Immagine") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val updatedProduct = prod.copy(
                    title = title,
                    price = price.toDoubleOrNull() ?: prod.price,
                    description = description,
                    available = available,
                    img_url = imgUrl
                )
                vm.updateProduct(updatedProduct)
                adminNavController.popBackStack() // Torna alla schermata precedente
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salva")
        }
    }
}

