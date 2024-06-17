package com.unical.amazing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.model.Item
import com.unical.amazing.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.unical.amazing.model.account.WishlistModel
import kotlinx.coroutines.Dispatchers

class AccountViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        // Simuliamo una chiamata di rete per ottenere i dati dell'utente,da aggiornare con codice connessione a db
        viewModelScope.launch(Dispatchers.IO) {

            val user = User(
                id = "12345",
                firstName = "John ",
                lastName = "Doe",
                phone = 123456789,
                CAP = 88100,
                city = "Catanzaro",
                country = "Italia",
                email = "johndoe@example.com",
                address = "via del tutto eccezionale",
                wishlistModels = listOf(),
                profilePictureUrl = "",
                orders = listOf()
            )
            _user.value = user
        }
    }

    fun updateUserProfilePicture(newProfilePictureUri: String) {
        val currentUser = _user.value ?: return
        val updatedUser = currentUser.copy(profilePictureUrl = newProfilePictureUri)
        _user.value = updatedUser
    }

    fun updateUser(updatedUser: User) {
        _user.value = updatedUser
        // Qui aggiungere la logica per salvare l'utente aggiornato nel database
    }



    fun createWishlist() {
        viewModelScope.launch {
            // Chiamata al tuo database o backend per creare una nuova lista dei desideri
            // Ad esempio, se stai usando Room:
            // wishlistDao.insert(Wishlist())
        }
    }

    fun removeWishlist(wishlistModel: WishlistModel) {
        viewModelScope.launch {
            // Chiamata al tuo database o backend per rimuovere la lista dei desideri
            // Ad esempio, se stai usando Room:
            // wishlistDao.delete(wishlist)
        }
    }

    fun removeItemFromWishlist(item: Item) {
        viewModelScope.launch {
            // Chiamata al tuo database o backend per rimuovere l'elemento dalla lista dei desideri
            // Ad esempio, se stai usando Room:
            // itemDao.delete(item)
        }
    }

    fun updateWishlistVisibility(wishlistModel: WishlistModel, isPublic: Boolean) {
        viewModelScope.launch {
            // Aggiorna la visibilità della lista dei desideri nel tuo database o backend
            // Ad esempio, se stai usando Room:
            // wishlist.isPublic = isPublic
            // wishlistDao.update(wishlist)
        }
    }

}


/*
prendere esempio per gestione thread nelle altre classi

* class HomeViewModel : ViewModel() {
    var productList by mutableStateOf(emptyList<ProductDto>())



    fun loadProducts(context: android.content.Context) {
        viewModelScope.launch(Dispatchers.IO){   //chiamata esterna -> usa un altro thread per chiamate in/out
            try {
                // Ottieni la lista dei prodotti dall'API
                productList = ProductApi().getAll().toList()
            } catch (e: Exception) {
                e.printStackTrace()
                // Gestisci gli errori di connessione o altri problemi
                Toast.makeText(context, "Errore durante il recupero dei prodotti", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
*
* */