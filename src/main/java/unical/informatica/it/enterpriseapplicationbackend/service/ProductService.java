package unical.informatica.it.enterpriseapplicationbackend.service;

import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling product actions.
 */
@Service
public class ProductService {

  /** The Product DAO. */
  private ProductDAO productDAO;

  /**
   * Constructor for spring injection.
   * @param productDAO
   */
  public ProductService(ProductDAO productDAO) {
    this.productDAO = productDAO;
  }

  /**
   * Gets the all products available.
   * @return The list of products.
   */
  public List<Product> getProducts() {
    return productDAO.findByAvailableTrue();
  }

  public List<Product> getProdsbyname(String name) {
    return productDAO.findByTitleLike(name);
  }

}
