package unical.informatica.it.enterpriseapplicationbackend.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unical.informatica.it.enterpriseapplicationbackend.model.Address;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.AddressDAO;

@Service
public class AddressService {
    private final AddressDAO addressDao;

    public AddressService(AddressDAO addressDao) {
        this.addressDao = addressDao;
    }

    @Transactional
    public Address saveAddress(Address address) {
        return addressDao.save(address);
    }
}
