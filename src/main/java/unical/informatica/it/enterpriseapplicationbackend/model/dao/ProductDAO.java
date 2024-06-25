package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import org.springframework.data.repository.ListCrudRepository;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


/**
 * Data Access Object for accessing Product data.
 */
public interface ProductDAO extends ListCrudRepository<Product, Long> {
    List<Product> findByAvailableTrue();

    @Query("select p from Product p where p.title ilike '%1%'")
    List<Product> findByTitleLike(String title);

    @Query("select p from Product p where p.id = ?1")
    Optional<Product> find(Long id);
}
