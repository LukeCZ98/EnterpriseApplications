package unical.informatica.it.enterpriseapplicationbackend.api.controller.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Wishlist;
import unical.informatica.it.enterpriseapplicationbackend.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<Wishlist>> getWishlistsByUser(@AuthenticationPrincipal LocalUser user) {
        List<Wishlist> wishlists = wishlistService.findByUser(user);
        if (wishlists != null) {
            return new ResponseEntity<>(wishlists, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shared")
    public ResponseEntity<List<Wishlist>> getWishlistsSharedWithUser(@AuthenticationPrincipal LocalUser user) {
        List<Wishlist> sharedWishlists = wishlistService.findSharedWithUser(user);
        return new ResponseEntity<>(sharedWishlists, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist, @AuthenticationPrincipal LocalUser user) {
        try {
            wishlist.setUser(user);  // Associare l'utente autenticato alla wishlist
            Wishlist createdWishlist = wishlistService.save(wishlist);
            return new ResponseEntity<>(createdWishlist, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Long id, @RequestBody Wishlist wishlist, @AuthenticationPrincipal LocalUser user) {
        try {
            if (!wishlist.getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Wishlist updatedWishlist = wishlistService.updateWishlist(id, wishlist);
            return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id, @AuthenticationPrincipal LocalUser user) {
        Optional<Wishlist> wishlist = wishlistService.findById(id);
        if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        wishlistService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Altri endpoint per gestire le wishlists
}
