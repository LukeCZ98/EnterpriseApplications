package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import org.springframework.lang.NonNull;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;
import unical.informatica.it.enterpriseapplicationbackend.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the LocalUser data.
 */
public interface LocalUserDAO extends ListCrudRepository<LocalUser, Long> {

  Optional<LocalUser> findByUsernameIgnoreCase(String username);

  Optional<LocalUser> findByEmailIgnoreCase(String email);



}
