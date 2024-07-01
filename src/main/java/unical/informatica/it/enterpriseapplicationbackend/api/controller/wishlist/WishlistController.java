package unical.informatica.it.enterpriseapplicationbackend.api.controller.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unical.informatica.it.enterpriseapplicationbackend.mappers.WishlistMapper;
import unical.informatica.it.enterpriseapplicationbackend.mappers.WishlistSharedUserMapper;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.Wishlist;
import unical.informatica.it.enterpriseapplicationbackend.model.WishlistSharedUser;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistDTO;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistSharedUserDTO;
import unical.informatica.it.enterpriseapplicationbackend.service.ProductService;
import unical.informatica.it.enterpriseapplicationbackend.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import unical.informatica.it.enterpriseapplicationbackend.service.WishlistSharedUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//CONTROLLER FUNZIONANTE PER INTERO

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    private final WishlistMapper wishlistMapper;

    private final WishlistSharedUserMapper wishlistSharedUserMapper;
    private final WishlistSharedUserService wishlistSharedUserService;

    @Autowired
    public WishlistController(WishlistService wishlistService, WishlistMapper wishlistMapper, WishlistSharedUserMapper wishlistSharedUserMapper, WishlistSharedUserService wishlistSharedUserService) {
        this.wishlistService = wishlistService;
        this.wishlistMapper = wishlistMapper;
        this.wishlistSharedUserMapper = wishlistSharedUserMapper;
        this.wishlistSharedUserService = wishlistSharedUserService;
    }

    @GetMapping("/all")  // FUNZIONA
    public ResponseEntity<List<WishlistDTO>> getWishlists(@AuthenticationPrincipal LocalUser user) {
        List<Wishlist> wishlists = wishlistService.findByUser(user);
        if (wishlists != null && !wishlists.isEmpty()) {
            List<WishlistDTO> wishlistDTOs = wishlists.stream()
                    .map(wishlistMapper::toDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(wishlistDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shared")  //FUNZIONA
    public ResponseEntity<List<WishlistSharedUserDTO>> getWishlistsSharedWithUser(@AuthenticationPrincipal LocalUser user) {
        List<WishlistSharedUser> sharedWishlists = wishlistSharedUserService.findSharedWithUser(user);
        if (sharedWishlists != null && !sharedWishlists.isEmpty()) {
            List<WishlistSharedUserDTO> wishlistSharedUserDTOs = sharedWishlists.stream()
                    .map(wishlistSharedUserMapper::toDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(wishlistSharedUserDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @DeleteMapping("/delete/{id}") //FUNZIONA
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id, @AuthenticationPrincipal LocalUser user) {
        Optional<Wishlist> wishlist = wishlistService.findById(id);
        if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        wishlistService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PostMapping("/addItem/{wid}/{itemId}") //FUNZIONA
    public ResponseEntity<Wishlist> addItemToWishlist(@PathVariable Long wid, @PathVariable Long itemId, @AuthenticationPrincipal LocalUser user) {
        try {
            Optional<Wishlist> wishlist = wishlistService.findById(wid);
            if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Product item = productService.findById(itemId);
            if (item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            wishlist.get().getItems().add(item);
            Wishlist updatedWishlist = wishlistService.updateWishlist(wishlist.get().getId(), wishlist.get());
            return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/removeItem/{wid}/{itemId}") //FUNZIONA
    public ResponseEntity<Wishlist> removeItemFromWishlist(@PathVariable Long wid, @PathVariable Long itemId, @AuthenticationPrincipal LocalUser user) {
        try {
            Optional<Wishlist> wishlist = wishlistService.findById(wid);
            if (wishlist.isEmpty() || !wishlist.get().getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Product item = productService.findById(itemId);
            if (item == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            wishlist.get().getItems().remove(item);
            Wishlist updatedWishlist = wishlistService.updateWishlist(wishlist.get().getId(), wishlist.get());
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
