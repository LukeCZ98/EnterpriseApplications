package unical.informatica.it.enterpriseapplicationbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unical.informatica.it.enterpriseapplicationbackend.model.Wishlist;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WishlistDAO;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistDAO wishlistDAO;

    public List<Wishlist> findByUser(LocalUser user) {
        return wishlistDAO.findByUser(user);
    }

    public Optional<Wishlist> findById(Long id) {
        return wishlistDAO.findById(id);
    }

    public List<Wishlist> findSharedWithUser(LocalUser user) {
        return wishlistDAO.findBySharedWith(user);
    }

    public Wishlist save(Wishlist wishlist) throws Exception {
        // Controllo per evitare duplicati
        List<Wishlist> existingWishlist = wishlistDAO.findByNameAndUser(wishlist.getName(), wishlist.getUser());
        if (!existingWishlist.isEmpty()) {
            System.out.println("Wishlist already exists"+ existingWishlist);
            throw new Exception("A wishlist with the same name already exists for this user.");
        }
        return wishlistDAO.save(wishlist);
    }

    public void delete(Wishlist wishlist) {
        wishlistDAO.delete(wishlist);
    }


    public Wishlist updateWishlist(Long id, Wishlist wishlist) throws Exception {
        Optional<Wishlist> existingWishlist = wishlistDAO.findById(id);
        if (existingWishlist.isEmpty()) {
            throw new Exception("Wishlist not found.");
        }
        Wishlist updatedWishlist = existingWishlist.get();
        updatedWishlist.setName(wishlist.getName());
        updatedWishlist.setItems(wishlist.getItems());
        return wishlistDAO.save(updatedWishlist);
    }

    public List<Wishlist> findPublicWishlists() {
        return wishlistDAO.findByIsPublic();
    }

}
