package unical.informatica.it.enterpriseapplicationbackend.model.dto;

import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;

import java.util.List;

public class WishlistDTO {
    private Long id;
    private String name;
    private String visibility;
//    private LocalUser user;
    private List<Product> items;
    private List<LocalUser> sharedWith;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

//    public LocalUser getUser() {
//        return user;
//    }
//
//    public void setUser(LocalUser user) {
//        this.user = user;
//    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public List<LocalUser> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(List<LocalUser> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
