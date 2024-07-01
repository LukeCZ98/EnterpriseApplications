package unical.informatica.it.enterpriseapplicationbackend.model.dto;


public class WishlistSharedUserDTO {
    private Long id;
    private WishlistDTO wishlist;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WishlistDTO getWishlist() {
        return wishlist;
    }

    public void setWishlist(WishlistDTO wishlist) {
        this.wishlist = wishlist;
    }

}
