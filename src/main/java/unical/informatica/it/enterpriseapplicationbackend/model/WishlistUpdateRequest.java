package unical.informatica.it.enterpriseapplicationbackend.model;

import java.util.List;

public class WishlistUpdateRequest {
    private Wishlist wishlist;
    private List<String> users;

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}

