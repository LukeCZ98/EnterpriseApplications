package unical.informatica.it.enterpriseapplicationbackend.api.controller.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrderQuantities;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.LocalUserDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.WebOrderDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WebOrderDTO;
import unical.informatica.it.enterpriseapplicationbackend.mappers.WebOrderMapper;
import unical.informatica.it.enterpriseapplicationbackend.model.dto.WebOrderQuantitiesDTO;
import unical.informatica.it.enterpriseapplicationbackend.mappers.WebOrderQuantitiesMapper;
import unical.informatica.it.enterpriseapplicationbackend.service.AddressService;
import unical.informatica.it.enterpriseapplicationbackend.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import unical.informatica.it.enterpriseapplicationbackend.service.ProductService;
import unical.informatica.it.enterpriseapplicationbackend.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//CONTROLLER FUNZIONANTE PER INTERO
/**
 * Controller to handle requests to create, update and view orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final UserService userService;

  private final AddressService addressService;
  private final WebOrderDAO webOrderDAO;
  private final WebOrderQuantitiesMapper webOrderQuantitiesMapper;
  /** The Order Service. */

  @Autowired
  private ProductService productService;

  @Autowired
  private OrderService orderService;

    /**
   * Constructor for spring injection.
   * @param orderService
   */
  public OrderController(OrderService orderService, UserService userService, LocalUserDAO localUserDAO, AddressService addressService, WebOrderDAO webOrderDAO, WebOrderQuantitiesMapper webOrderQuantitiesMapper) {
    this.orderService = orderService;
    this.userService = userService;
      this.addressService = addressService;
    this.webOrderDAO = webOrderDAO;
      this.webOrderQuantitiesMapper = webOrderQuantitiesMapper;
  }

  /**
   * Endpoint to get all orders for a specific user.
   * @param user The user provided by spring security context.
   * @return The list of orders the user had made.
   */
  @GetMapping("/all") //FUNZIONA
  public List<WebOrderDTO> getOrders(@AuthenticationPrincipal LocalUser user) {
    List<WebOrder> orders = user.getRole() ? orderService.getAll() : orderService.getOrders(user);
    return orders.stream()
            .map(WebOrderMapper::toDTO)
            .collect(Collectors.toList());
  }



  @PostMapping("/del")   //FUNZIONA
  public void delOrder(@AuthenticationPrincipal LocalUser user, @RequestBody WebOrder order) {
    if(user.getRole())
      orderService.removeOrder(order);
  }



  @PostMapping("/checkout") //FUNZIONA
  public List<WebOrderQuantities> checkout(@AuthenticationPrincipal LocalUser user, @RequestBody List<WebOrderQuantitiesDTO> quantitiesDTO) {
    WebOrder order = new WebOrder();
    order.setUser(user);
    order.setAddress(user.getAddresses().get(0));
    WebOrder ord = orderService.addOrder(order);

    List<WebOrderQuantities> quantities = new ArrayList<>();
    for (WebOrderQuantitiesDTO quantityDTO : quantitiesDTO) {
      WebOrderQuantities quantity = webOrderQuantitiesMapper.toEntity(quantityDTO);
      quantity.setOrder(ord);
      quantities.add(quantity);
    }

    return orderService.addQuantities(quantities);
  }





  @PostMapping("/find") //FUNZIONA
  public List<WebOrder> findbyuser(@AuthenticationPrincipal LocalUser user, @RequestBody LocalUser usr) {
    if(user.getRole()){
        return orderService.getOrders(usr);
    }
    return null;
  }



}
