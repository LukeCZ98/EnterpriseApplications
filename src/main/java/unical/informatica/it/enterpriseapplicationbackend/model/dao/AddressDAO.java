package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import unical.informatica.it.enterpriseapplicationbackend.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

/**
 * Data Access Object for the Address data.
 */
public interface AddressDAO extends ListCrudRepository<Address, Long> {

  List<Address> findByUser_Id(Long id);

}
