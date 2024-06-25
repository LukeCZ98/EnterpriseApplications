package unical.informatica.it.enterpriseapplicationbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * A product available for purchasing.
 */
@Entity
@Table(name = "product")
public class Product {

  /** Unique id for the product. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  /** The name of the product. */
  @Column(name = "title", nullable = false, unique = true)
  private String title;
  /** The short description of the product. */
  @Column(name = "description", nullable = false)
  private String description;
  /** The long description of the product. */
  @Column(name = "img_url")
  private String img_url;
  /** The price of the product. */
  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "available", nullable = false)
  private Boolean available;


  /** The inventory of the product. */
  @JsonIgnore
  @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
  private Inventory inventory;

  /**
   * Gets the inventory of the product.
   * @return The inventory.
   */
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * Sets the inventory of the product.
   * @param inventory The inventory.
   */
  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  /**
   * Gets the price of the product.
   * @return The price.
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Sets the price of the product.
   * @param price The price.
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Gets the long description of the product.
   * @return The long description.
   */
  public String getImg_url() {
    return img_url;
  }

  /**
   * Sets the long description of the product.
   * @param img_url The long description.
   */
  public void setImg_url(String img_url) {
    this.img_url = img_url;
  }

  /**
   * Gets the short description of the product.
   * @return The short description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the short description of the product.
   * @param description The short description.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the name of the product.
   * @return The name.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the name of the product.
   * @param title The name.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the id of the product.
   * @return The id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id of the product.
   * @param id The id.
   */
  public void setId(Long id) {
    this.id = id;
  }


  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }
}