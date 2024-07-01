package unical.informatica.it.enterpriseapplicationbackend.model.dao;

import org.springframework.data.repository.ListCrudRepository;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrderQuantities;

public interface WebOrderQuantitiesDAO extends ListCrudRepository<WebOrderQuantities, Long> {
}