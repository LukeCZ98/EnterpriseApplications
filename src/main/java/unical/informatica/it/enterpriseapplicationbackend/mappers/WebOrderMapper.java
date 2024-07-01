package unical.informatica.it.enterpriseapplicationbackend.mappers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WebOrderDTO;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WebOrderQuantitiesDTO;

import java.util.List;

@Component
public class WebOrderMapper {

    private static WebOrderQuantitiesMapper quantitiesMapper;

    @Autowired
    public WebOrderMapper(WebOrderQuantitiesMapper quantitiesMapper) {
        WebOrderMapper.quantitiesMapper = quantitiesMapper;
    }

    public static WebOrderDTO toDTO(WebOrder order) {
        WebOrderDTO dto = new WebOrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setAddressId(order.getAddress().getId());
        List<WebOrderQuantitiesDTO> quantities = order.getQuantities().stream()
                .map(quantitiesMapper::toDTO)
                .collect(Collectors.toList());
        dto.setQuantities(quantities);
        return dto;
    }
}

