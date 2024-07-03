package unical.informatica.it.enterpriseapplicationbackend.model.dto;

import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.Visibility;

import java.util.List;

public class WishlistResponseDTO {
    private Long id;
    private String name;
    private Visibility visibility;
    private List<Product> items;

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

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
