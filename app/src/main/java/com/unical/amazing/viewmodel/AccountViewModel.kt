package com.unical.amazing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.model.Item
import com.unical.amazing.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.unical.amazing.model.Wishlist
class AccountViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        // Simuliamo una chiamata di rete per ottenere i dati dell'utente,da aggiornare con codice connessione a db
        viewModelScope.launch {

            val itm1: Item = Item("1231","prova1","prova1",22.22,100,"",true)
            val itm2: Item = Item("131","prova2","prova2",24.22,100,"",true)
            val itm3: Item = Item("123","prova3","prova3",27.22,100,"",true)

            val user = User(
                id = "12345",
                name = "John ",
                surname = "Doe",
                phone = 123456789,
                CAP = 88100,
                city = "Catanzaro",
                country = "Italia",
                email = "johndoe@example.com",
                address = "via del tutto eccezionale",
                wishlists = listOf(),
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

    fun removeItem(item: Item) {
        viewModelScope.launch {
            // Chiamata al tuo database o backend per rimuovere l'elemento
            // Ad esempio, se stai usando Room:
            // itemDao.delete(item)
        }
    }

    fun createWishlist() {
        viewModelScope.launch {
            // Chiamata al tuo database o backend per creare una nuova lista dei desideri
            // Ad esempio, se stai usando Room:
            // wishlistDao.insert(Wishlist())
        }
    }

    fun removeWishlist(wishlist: Wishlist) {
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

    fun updateWishlistVisibility(wishlist: Wishlist, isPublic: Boolean) {
        viewModelScope.launch {
            // Aggiorna la visibilità della lista dei desideri nel tuo database o backend
            // Ad esempio, se stai usando Room:
            // wishlist.isPublic = isPublic
            // wishlistDao.update(wishlist)
        }
    }

}
