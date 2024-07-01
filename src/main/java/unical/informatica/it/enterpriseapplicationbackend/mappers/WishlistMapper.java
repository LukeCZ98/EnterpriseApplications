package unical.informatica.it.enterpriseapplicationbackend.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unical.informatica.it.enterpriseapplicationbackend.model.Visibility;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistDTO;
import unical.informatica.it.enterpriseapplicationbackend.model.Wishlist;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.service.ProductService;
import unical.informatica.it.enterpriseapplicationbackend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    private final ProductService productService;
    private final UserService localUserService;

    @Autowired
    public WishlistMapper(ProductService productService, UserService localUserService) {
        this.productService = productService;
        this.localUserService = localUserService;
    }

    public WishlistDTO toDTO(Wishlist wishlist) {
        WishlistDTO dto = new WishlistDTO();
        dto.setId(wishlist.getId());
        dto.setName(wishlist.getName());
        dto.setVisibility(wishlist.getVisibility().name());
//        dto.setUser(wishlist.getUser());
        dto.setItems(wishlist.getItems());
        dto.setSharedWith(wishlist.getSharedWith());
        return dto;
    }

    public Wishlist toEntity(WishlistDTO dto) {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(dto.getId());
        wishlist.setName(dto.getName());
        wishlist.setVisibility(Visibility.valueOf(dto.getVisibility()));
//        wishlist.setUser(localUserService.findById(dto.getUser().getId()));
        List<Product> products = dto.getItems().stream().map(product -> productService.findById(product.getId())).collect(Collectors.toList());
        wishlist.setItems(products);
        List<LocalUser> sharedUsers = dto.getSharedWith().stream().map(user -> localUserService.findById(user.getId())).collect(Collectors.toList());
        wishlist.setSharedWith(sharedUsers);
        return wishlist;
    }
}
