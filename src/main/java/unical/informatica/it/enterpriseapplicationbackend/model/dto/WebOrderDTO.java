package unical.informatica.it.enterpriseapplicationbackend.model.dto;

import java.util.List;

public class WebOrderDTO {
    private Long id;
    private Long userId;
    private Long addressId;
    private List<WebOrderQuantitiesDTO> quantities;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public List<WebOrderQuantitiesDTO> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<WebOrderQuantitiesDTO> quantities) {
        this.quantities = quantities;
    }
}


