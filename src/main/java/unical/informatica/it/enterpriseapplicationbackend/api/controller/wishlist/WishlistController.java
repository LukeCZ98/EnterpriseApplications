package unical.informatica.it.enterpriseapplicationbackend.api.controller.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.Wishlist;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WishlistDAO;
import unical.informatica.it.enterpriseapplicationbackend.service.ProductService;
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

    @Autowired
    private ProductService productService;
    @Autowired
    private WishlistDAO wishlistDAO;

    @GetMapping  //FUNZIONA  -> aggiustare return
    public ResponseEntity<List<Wishlist>> getWishlists(@AuthenticationPrincipal LocalUser user) {
        List<Wishlist> wishlists = wishlistService.findByUser(user);
        if (wishlists != null) {
            return new ResponseEntity<>(wishlists, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shared") //FUNZIONA -> aggiustare return
    public ResponseEntity<List<Wishlist>> getWishlistsSharedWithUser(@AuthenticationPrincipal LocalUser user) {
        List<Wishlist> sharedWishlists = wishlistService.findSharedWithUser(user);
        return new ResponseEntity<>(sharedWishlists, HttpStatus.OK);
    }

    @PostMapping("/add")  //FUNZIONA
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

    @PutMapping("/update") //FUNZIONA
    public ResponseEntity<Wishlist> updateWishlist(@RequestBody Wishlist wishlist, @AuthenticationPrincipal LocalUser user) {
        try {
            Optional<Wishlist> existingWishlist = wishlistService.findById(wishlist.getId());
            if (existingWishlist.isEmpty() || !existingWishlist.get().getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            // Check if the new name is already used by another wishlist of the user
            List<Wishlist> userWishlists = wishlistService.findByUser(user);
            for (Wishlist wl : userWishlists) {
                if (wl.getName().equals(wishlist.getName()) && !wl.getId().equals(wishlist.getId())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);  // Conflict: Duplicate name
                }
            }

            existingWishlist.get().setName(wishlist.getName());
            existingWishlist.get().setItems(wishlist.getItems());

            Wishlist updatedWishlist = wishlistService.updateWishlist(wishlist.getId(), existingWishlist.get());
            return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWishlist(@RequestParam Long id, @AuthenticationPrincipal LocalUser user) {
        Optional<Wishlist> wishlist = wishlistService.findById(id);
        if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        wishlistService.delete(wishlist.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/addItem")
    public ResponseEntity<Wishlist> addItemToWishlist(@RequestParam Long wishlistId, @RequestParam Long itemId, @AuthenticationPrincipal LocalUser user) {
        try {
            Optional<Wishlist> wishlist = wishlistService.findById(wishlistId);
            if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Product item = productService.findById(itemId);
            if (item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            wishlist.get().getItems().add(item);
            Wishlist updatedWishlist = wishlistService.save(wishlist.get());
            return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/removeItem")
    public ResponseEntity<Wishlist> removeItemFromWishlist(@RequestParam Long wishlistId, @RequestParam Long itemId, @AuthenticationPrincipal LocalUser user) {
        try {
            Optional<Wishlist> wishlist = wishlistService.findById(wishlistId);
            if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Product item = productService.findById(itemId);
            if (item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            wishlist.get().getItems().remove(item);
            Wishlist updatedWishlist = wishlistService.save(wishlist.get());
            return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/public") //FUNZIONA
    public ResponseEntity<List<Wishlist>> getPublicWishlists(@AuthenticationPrincipal LocalUser user) {
        List<Wishlist> publicWishlists = wishlistService.findPublicWishlists();
        return new ResponseEntity<>(publicWishlists, HttpStatus.OK);
    }

}
