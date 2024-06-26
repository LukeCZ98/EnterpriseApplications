package unical.informatica.it.enterpriseapplicationbackend.api.controller.product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import unical.informatica.it.enterpriseapplicationbackend.api.model.ProductBody;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.ProductDAO;
import unical.informatica.it.enterpriseapplicationbackend.service.ProductService;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

/**
 * Controller to handle the creation, updating & viewing of products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductDAO productDAO;
  /**
   * The Product Service.
   */
  private ProductService productService;

  /**
   * Constructor for spring injection.
   *
   * @param productService
   */
  public ProductController(ProductService productService, ProductDAO productDAO) {
    this.productService = productService;
    this.productDAO = productDAO;
  }

  /**
   * Gets the list of products available.
   *
   * @return The list of products.
   */
  @GetMapping("/all")
  public List<Product> getProducts() {
    return productService.getProducts();
  }


  @GetMapping("/find/{name}")
  public List<Product> getProdsbyName(@PathVariable String name) {
    return productService.getProdsbyname(name);
  }

  // @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{productId}")
  public ResponseEntity<Product> patchProduct(
          @AuthenticationPrincipal LocalUser user,
          @PathVariable Long productId,
          @RequestBody Product updatedProduct) {


    // Ensure the updated product has the correct ID
    if (updatedProduct.getId() == productId) {
      // Retrieve the original product from the database
      Optional<Product> opOriginalProduct = productDAO.findById(productId);
      if (opOriginalProduct.isPresent()) {
        Product originalProduct = opOriginalProduct.get();

        // Perform updates only on specified fields (title in this example)
        if (updatedProduct.getTitle() != null && !updatedProduct.getTitle().isEmpty()) {
          originalProduct.setTitle(updatedProduct.getTitle());
        }
        // You can add similar checks and updates for other fields like description, price, etc.

        // Save the updated product
        Product savedProduct = productDAO.save(originalProduct);

        return ResponseEntity.ok(savedProduct);
      }
    }

    // If any condition fails, return bad request
    return ResponseEntity.badRequest().build();
  }



//metodi funzionanti inserire verifica utente admin -> update add e delete
@PostMapping("/update")
public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {

        if(productService.updateProduct(product))
          return ResponseEntity.ok().build();
        else{
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("errore");
        }

}


@PostMapping("/add")
public Product addProduct(@Valid @RequestBody Product product) {

   return productDAO.save(product);

}


@PostMapping("/delete")
public ResponseEntity<String> delProduct(@Valid @RequestBody Product product) {
    try{
      productService.deleteProduct(product);
      return ResponseEntity.ok().build();
    }
    catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("errore");
    }
}





}
