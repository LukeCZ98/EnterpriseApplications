package unical.informatica.it.enterpriseapplicationbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> items = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "wishlist_shared_users",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<LocalUser> sharedWith = new ArrayList<>();

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

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }

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

