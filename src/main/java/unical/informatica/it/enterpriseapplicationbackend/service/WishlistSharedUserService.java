package unical.informatica.it.enterpriseapplicationbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WishlistSharedUser;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WishlistSharedUserDAO;

import java.util.List;

@Service
public class WishlistSharedUserService {

    private final WishlistSharedUserDAO wishlistSharedUserDAO;

    @Autowired
    public WishlistSharedUserService(WishlistSharedUserDAO wishlistSharedUserDAO) {
        this.wishlistSharedUserDAO = wishlistSharedUserDAO;
    }

    public List<WishlistSharedUser>findSharedWithUser(LocalUser user){
        return wishlistSharedUserDAO.findByUser(user);
    }

}
