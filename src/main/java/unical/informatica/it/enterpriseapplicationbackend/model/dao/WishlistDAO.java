package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Visibility;
import unical.informatica.it.enterpriseapplicationbackend.model.Wishlist;

import java.util.List;

public interface WishlistDAO extends ListCrudRepository<Wishlist, Long> {
    List<Wishlist> findByUser(LocalUser user);

    @Query("select w from Wishlist w where w.visibility = 'PUBLIC'")
    List<Wishlist> findByIsPublic();

    List<Wishlist> findBySharedWith(LocalUser sharedWith);

    List<Wishlist> findByNameAndUser(String name, LocalUser user);

}