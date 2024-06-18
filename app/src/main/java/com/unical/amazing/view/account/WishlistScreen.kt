package com.unical.amazing.view.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.model.Item
import com.unical.amazing.model.account.WishlistModel
import com.unical.amazing.viewmodel.AccountViewModel

@Composable
fun WishlistScreen(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()

    userState.value?.let { user ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Wishlist", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))
            user.wishlistModels?.forEach { wishlist ->
                WishlistView(wishlist, accountViewModel)
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
            Button(onClick = { accountViewModel.createWishlist() }) {
                Text("Crea nuova lista dei desideri")
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun WishlistView(wishlistModel: WishlistModel, accountViewModel: AccountViewModel){
    var isPublic by remember { mutableStateOf(wishlistModel.isPublic) }
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wishlistModel.name, style = MaterialTheme.typography.body1, color = Color.Black)
            wishlistModel.items.forEach { item ->
                ItemView(item, accountViewModel)
            }
            Button(onClick = { accountViewModel.removeWishlist(wishlistModel) }) {
                Text("Rimuovi lista dei desideri")
            }
            Checkbox(checked = isPublic, onCheckedChange = { isChecked ->
                isPublic = isChecked
                accountViewModel.updateWishlistVisibility(wishlistModel, isPublic)
            })
            Text("Pubblico")
        }
    }
}

@Composable
fun ItemView(item: Item, accountViewModel: AccountViewModel){
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.body1, color = Color.Black)
            Text(text = "Prezzo: ${item.price}", style = MaterialTheme.typography.body2, color = Color.Black)
            Button(onClick = { accountViewModel.removeItemFromWishlist(item) }) {
                Text("Rimuovi dall'elenco dei desideri")
            }
        }
    }
}
