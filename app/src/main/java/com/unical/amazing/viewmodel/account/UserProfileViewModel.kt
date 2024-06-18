package com.unical.amazing.viewmodel.account

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.model.Item
import com.unical.amazing.model.account.WishlistModel
import com.unical.amazing.viewmodel.auth.AuthViewModel
import io.swagger.client.apis.UserApi
import io.swagger.client.models.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserProfileViewModel(context: Context) : ViewModel() {
    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> get() = _user
    private val token = AuthViewModel(context).getToken()
    private val usr = UserApi(context)

    init {
        fetchUserData()
    }



    private fun fetchUserData() {
        // Simuliamo una chiamata di rete per ottenere i dati dell'utente,da aggiornare con codice connessione a db
        viewModelScope.launch(Dispatchers.IO) {
            val user = token?.let { usr.account(it) }
            _user.value = user
        }
    }

    fun updateUserProfilePicture(newProfilePictureUri: String) {
        val currentUser = _user.value ?: return
        val updatedUser = currentUser.copy(picurl = newProfilePictureUri)
        _user.value = updatedUser
    }

    fun updateUser(updatedUser: UserDto) {
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
    // Aggiungi qui eventuali variabili di stato necessarie per la visualizzazione e la modifica dei dati dell'utente

    // Aggiungi qui i metodi per recuperare e salvare i dati dell'utente dal database o dal server

}
