package unical.informatica.it.enterpriseapplicationbackend.model.dto;

import java.util.List;

public class WebOrderBodyDTO {
    private String username;
    private List<WebOrderQuantitiesDTO> quantities;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<WebOrderQuantitiesDTO> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<WebOrderQuantitiesDTO> quantities) {
        this.quantities = quantities;
    }
}

