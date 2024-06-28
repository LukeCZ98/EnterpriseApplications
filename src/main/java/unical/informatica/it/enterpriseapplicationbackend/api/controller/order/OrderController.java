package unical.informatica.it.enterpriseapplicationbackend.api.controller.order;

import org.springframework.web.bind.annotation.*;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.WebOrder;
import unical.informatica.it.enterpriseapplicationbackend.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

/**
 * Controller to handle requests to create, update and view orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  /** The Order Service. */
  private OrderService orderService;

  /**
   * Constructor for spring injection.
   * @param orderService
   */
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Endpoint to get all orders for a specific user.
   * @param user The user provided by spring security context.
   * @return The list of orders the user had made.
   */
  @GetMapping
  public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user) {
    return orderService.getOrders(user);
  }

  //TODO -> testare metodi sottostanti
  @PostMapping("/add")
  public WebOrder addOrder(@AuthenticationPrincipal LocalUser user, @RequestBody WebOrder order) {
    order.setUser(user);
    order.setAddress(user.getAddresses().get(0));
    return orderService.addOrder(order);
  }

  @PostMapping("/del")
  public void delOrder(@AuthenticationPrincipal LocalUser user, @RequestBody WebOrder order) {
    if(user.getRole())
      orderService.removeOrder(order);
  }

  @PostMapping("/update")
  public void updOrder(@AuthenticationPrincipal LocalUser user, @RequestBody WebOrder order) {
    if(user.getRole())
      orderService.updateOrder(order);
  }

}
