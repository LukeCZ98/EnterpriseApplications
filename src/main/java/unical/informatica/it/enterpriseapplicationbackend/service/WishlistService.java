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

    public void delete(Long id) {
        wishlistDAO.deleteById(id);
    }


    public Wishlist updateWishlist(Long id, Wishlist wishlist) throws Exception {
        Optional<Wishlist> existingWishlist = wishlistDAO.findById(id);
        if (existingWishlist.isPresent()) {
            Wishlist existing = existingWishlist.get();
            // Controllo per evitare duplicati se il nome è cambiato
            if (!existing.getName().equals(wishlist.getName())) {
                List<Wishlist> wishlistWithSameName = wishlistDAO.findByNameAndUser(wishlist.getName(), wishlist.getUser());
                if (wishlistWithSameName!=null) {
                    throw new Exception("A wishlist with the same name already exists for this user.");
                }
            }
            // Aggiorna i campi della wishlist
            existing.setName(wishlist.getName());
            existing.setVisibility(wishlist.getVisibility());
            existing.setProducts(wishlist.getProducts());
            return wishlistDAO.save(existing);
        } else {
            throw new Exception("Wishlist not found.");
        }
    }

}
