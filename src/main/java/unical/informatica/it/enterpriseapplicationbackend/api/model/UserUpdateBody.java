package unical.informatica.it.enterpriseapplicationbackend.api.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class UserUpdateBody {

    @Pattern(regexp = "^[0-9]{10,15}$")
    private String phone;

    @Pattern(regexp = "^[\\w\\s,.#-]{1,500}$")
    @NotBlank
    private String address;

    @Pattern(regexp = "^[a-zA-Z\\s]{1,20}$")
    @NotBlank
    private String city;

    @Pattern(regexp = "^[a-zA-Z\\s]{1,30}$")
    @NotBlank
    private String country;


    private Integer cap;

    // Getter e Setter


    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getCap() { return cap; }
    public void setCap(Integer cap) { this.cap = cap; }
}
