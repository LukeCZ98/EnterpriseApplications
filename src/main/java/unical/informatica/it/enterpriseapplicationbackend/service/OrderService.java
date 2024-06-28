package unical.informatica.it.enterpriseapplicationbackend.service;

import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WebOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  public WebOrder addOrder(WebOrder order) {
    return webOrderDAO.save(order);
  }

  public void removeOrder(WebOrder order) {
    webOrderDAO.delete(order);
  }

  public void updateOrder(WebOrder order) {
    Optional<WebOrder> ord = webOrderDAO.findById(order.getId());
    if (ord.isPresent()) {
      WebOrder ordn = ord.get();
      ordn.setAddress(order.getAddress());
      ordn.setQuantities(order.getQuantities());
      webOrderDAO.save(ordn);
    }
  }

}
