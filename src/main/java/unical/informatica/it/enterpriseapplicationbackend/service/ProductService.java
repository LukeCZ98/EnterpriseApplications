package unical.informatica.it.enterpriseapplicationbackend.service;

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

/*
  public void updateProduct(Product product) {
    productDAO.updateTitleAndDescriptionAndImg_urlAndPriceAndAvailableAndInventoryByTitle(product.getTitle())
  }*/



  //TODO: TEST THIS METHOD
  public void updateProductTitle(String oldTitle, String title){
    Optional<Product> product = productDAO.findByTitle(oldTitle);
    if(product.isPresent()){
      product.get().setTitle(title);
    }
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
