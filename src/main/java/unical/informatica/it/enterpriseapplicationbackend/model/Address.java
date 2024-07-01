package unical.informatica.it.enterpriseapplicationbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

/**
 * Address for the user to be billed/delivered to.
 */
@Entity
@Table(name = "address")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {

  /** Unique id for the address. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  /** The first line of address. */
  @Column(name = "address_line_1", nullable = true, length = 512)
  private String address;

  /** The second line of address. */
  @JsonIgnore
  @Column(name = "address_line_2", length = 512)
  private String addressLine2;
  /** The city of the address. */
  @Column(name = "city", nullable = true)
  private String city;
  /** The country of the address. */
  @Column(name = "country", nullable = true, length = 75)
  private String country;

  @Column(name = "phone", nullable = true, length = 12)
  private String phone;

  @Column(name = "CAP", nullable = true, length = 5)
  private Integer CAP;

  /** The user the address is associated with. */
  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private LocalUser user;

  /**
   * Gets the user.
   * @return The user.
   */
  public LocalUser getUser() {
    return user;
  }

  /**
   * Sets the user.
   * @param user User to be set.
   */
  public void setUser(LocalUser user) {
    this.user = user;
  }

  /**
   * Gets the country.
   * @return The country.
   */
  public String getCountry() {
    return country;
  }

  /**
   * Sets the country.
   * @param country The country to be set.
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Gets the city.
   * @return The city.
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the city.
   * @param city The city to be set.
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets the Address Line 2.
   * @return The second line of the address.
   */
  public String getAddressLine2() {
    return addressLine2;
  }

  /**
   * Sets the second line of address.
   * @param addressLine2 The second line of address.
   */
  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  /**
   * Gets the Address Line 1.
   * @return The first line of the address.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the first line of address.
   * @param address The first line of address.
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Gets the ID of the address.
   * @return The ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the address.
   * @param id The ID.
   */
  public void setId(Long id) {
    this.id = id;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getCAP() {
    return CAP;
  }

  public void setCAP(Integer CAP) {
    this.CAP = CAP;
  }
}