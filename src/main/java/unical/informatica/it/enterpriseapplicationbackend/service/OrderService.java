package unical.informatica.it.enterpriseapplicationbackend.service;

import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WebOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling order actions.
 */
@Service
public class OrderService {

  /** The Web Order DAO. */
  private WebOrderDAO webOrderDAO;

  /**
   * Constructor for spring injection.
   * @param webOrderDAO
   */
  public OrderService(WebOrderDAO webOrderDAO) {
    this.webOrderDAO = webOrderDAO;
  }

  /**
   * Gets the list of orders for a given user.
   * @param user The user to search for.
   * @return The list of orders.
   */
  public List<WebOrder> getOrders(LocalUser user) {
    return webOrderDAO.findByUser(user);
  }

}
