package unical.informatica.it.enterpriseapplicationbackend.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unical.informatica.it.enterpriseapplicationbackend.model.WishlistSharedUser;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistSharedUserDTO;

@Component
public class WishlistSharedUserMapper {

    private final WishlistMapper wishlistMapper;

    @Autowired
    public WishlistSharedUserMapper(WishlistMapper wishlistMapper) {
        this.wishlistMapper = wishlistMapper;
    }

    public WishlistSharedUserDTO toDTO(WishlistSharedUser sharedUser) {
        WishlistSharedUserDTO dto = new WishlistSharedUserDTO();
        dto.setId(sharedUser.getId());
        dto.setWishlist(wishlistMapper.toDTO(sharedUser.getWishlist()));
        return dto;
    }

    public WishlistSharedUser toEntity(WishlistSharedUserDTO dto) {
        WishlistSharedUser sharedUser = new WishlistSharedUser();
        sharedUser.setId(dto.getId());
        sharedUser.setWishlist(wishlistMapper.toEntity(dto.getWishlist()));
        return sharedUser;
    }
}
