package unical.informatica.it.enterpriseapplicationbackend.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unical.informatica.it.enterpriseapplicationbackend.model.Address;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrderQuantities;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WebOrderBodyDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebOrderBodyMapper {

    private final WebOrderQuantitiesMapper quantitiesMapper;

    @Autowired
    public WebOrderBodyMapper(WebOrderQuantitiesMapper quantitiesMapper) {
        this.quantitiesMapper = quantitiesMapper;
    }

    public WebOrder toEntity(WebOrderBodyDTO dto, LocalUser user, Address address) {
        WebOrder order = new WebOrder();
        order.setUser(user);
        order.setAddress(address);
        List<WebOrderQuantities> quantities = dto.getQuantities().stream()
                .map(quantitiesMapper::toEntity)
                .collect(Collectors.toList());
        order.setQuantities(quantities);
        return order;
    }
}


