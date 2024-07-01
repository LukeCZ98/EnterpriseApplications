package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import org.springframework.data.repository.ListCrudRepository;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WishlistSharedUser;

import java.util.List;

public interface WishlistSharedUserDAO extends ListCrudRepository<WishlistSharedUser, Long> {
    List<WishlistSharedUser> findByUser(LocalUser user);

}