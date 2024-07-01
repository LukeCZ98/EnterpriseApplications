package unical.informatica.it.enterpriseapplicationbackend.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrderQuantities;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WebOrderQuantitiesDTO;
import unical.informatica.it.enterpriseapplicationbackend.service.ProductService;


@Component
public class WebOrderQuantitiesMapper {

    private final ProductService productService;

    @Autowired
    public WebOrderQuantitiesMapper(ProductService productService) {
        this.productService = productService;
    }

    public WebOrderQuantitiesDTO toDTO(WebOrderQuantities quantity) {
        WebOrderQuantitiesDTO dto = new WebOrderQuantitiesDTO();
        dto.setId(quantity.getId());
        dto.setProductId(quantity.getProduct().getId());
        dto.setQuantity(quantity.getQuantity());
        return dto;
    }

    public WebOrderQuantities toEntity(WebOrderQuantitiesDTO dto) {
        WebOrderQuantities quantity = new WebOrderQuantities();
        Product product = productService.findById(dto.getProductId());
        quantity.setProduct(product);
        quantity.setQuantity(dto.getQuantity());
        return quantity;
    }
}



