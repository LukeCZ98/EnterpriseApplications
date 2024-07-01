package unical.informatica.it.enterpriseapplicationbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Product;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrderQuantities;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.LocalUserDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WebOrderDAO;
import org.springframework.stereotype.Service;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WebOrderQuantitiesDAO;

import java.util.List;

/**
 * Service for handling order actions.
 */
@Service
public class OrderService {

  private final UserService userService;
  private final LocalUserDAO localUserDAO;
  /** The Web Order DAO. */
  @Autowired
  private WebOrderDAO webOrderDAO;

  @Autowired
  private ProductService productService;

  @Autowired
  private WebOrderQuantitiesDAO webOrderQuantitiesDAO;


  /**
   * Constructor for spring injection.
   * @param webOrderDAO
   */
  public OrderService(WebOrderDAO webOrderDAO, UserService userService, LocalUserDAO localUserDAO) {
    this.webOrderDAO = webOrderDAO;
    this.userService = userService;
    this.localUserDAO = localUserDAO;
  }

  /**
   * Gets the list of orders for a given user.
   * @param user The user to search for.
   * @return The list of orders.
   */
  public List<WebOrder> getOrders(LocalUser user) {
    return webOrderDAO.findByUser(user);
  }

  public List<WebOrder> getAll(){return webOrderDAO.findAll();}

  @Transactional
  public WebOrder addOrder(WebOrder order) {
      return webOrderDAO.save(order);
  }

  public void removeOrder(WebOrder order) {
    webOrderDAO.delete(order);
  }


  public List<WebOrderQuantities> addQuantities(List<WebOrderQuantities> quantities) {
    return webOrderQuantitiesDAO.saveAll(quantities);
  }

}
