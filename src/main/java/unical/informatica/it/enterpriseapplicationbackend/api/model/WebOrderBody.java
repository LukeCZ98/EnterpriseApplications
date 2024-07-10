package unical.informatica.it.enterpriseapplicationbackend.api.model;

import jakarta.validation.constraints.Email;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrderQuantities;

import java.util.ArrayList;
import java.util.List;

public class WebOrderBody {


    private String username;

    private List<WebOrderQuantities> quantities = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public List<WebOrderQuantities> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<WebOrderQuantities> quantities) {
        this.quantities = quantities;
    }
}
