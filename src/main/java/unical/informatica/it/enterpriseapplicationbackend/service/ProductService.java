package unical.informatica.it.enterpriseapplicationbackend.service;

import jakarta.transaction.Transactional;
import unical.informatica.it.enterpriseapplicationbackend.api.model.ProductBody;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  //da mettere i dati che l'admin dovrebbe modificare
  public Boolean updateProduct(Product product) {
    Optional<Product> prod = productDAO.find(product.getId());
    if (prod.isPresent()) {
      Product prd = prod.get();
      prd.setDescription(product.getDescription());
      prd.setTitle(product.getTitle());
      prd.setPrice(product.getPrice());
      prd.setAvailable(product.getAvailable());
      productDAO.save(prd);
      return true;
    }
    return false;
  }

  //elimina prodotto
  public void deleteProduct(Product product) {
    Optional<Product> prod = productDAO.find(product.getId());
    if (prod.isPresent()) {
      productDAO.delete(prod.get());
    }
  }


  public void addProduct(Product product) {
    productDAO.save(product);
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
