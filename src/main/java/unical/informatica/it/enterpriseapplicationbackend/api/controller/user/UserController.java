package unical.informatica.it.enterpriseapplicationbackend.api.controller.user;

import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.AddressDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.LocalUserDAO;
import unical.informatica.it.enterpriseapplicationbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//CONTROLLER FUNZIONANTE PER INTERO

/**
 * Rest Controller for user data interactions.
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final LocalUserDAO localUserDAO;
  /** The Address DAO. */
  private AddressDAO addressDAO;
  private SimpMessagingTemplate simpMessagingTemplate;
  private UserService userService;

  /**
   * Constructor for spring injection.
   * @param addressDAO
   * @param simpMessagingTemplate
   * @param userService
   */
  public UserController(AddressDAO addressDAO,
                        SimpMessagingTemplate simpMessagingTemplate,
                        UserService userService, LocalUserDAO localUserDAO) {
    this.addressDAO = addressDAO;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.userService = userService;
    this.localUserDAO = localUserDAO;
  }



  @GetMapping("/all")  //FUNZIONA
  public ResponseEntity<List<LocalUser>> getAll(@AuthenticationPrincipal LocalUser user) {
    if (user.getRole()) {
      return ResponseEntity.ok(userService.findAll());
    }
    else
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }



  @GetMapping("/usr/{id}") //FUNZIONA
  public ResponseEntity<LocalUser> getUser(@AuthenticationPrincipal LocalUser user,@PathVariable Long id) {
    if (user.getRole()) {
        return ResponseEntity.ok(userService.findById(id));
    }
    else
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }



  @PostMapping("/del")//FUNZIONA
  public ResponseEntity<LocalUser> del(@AuthenticationPrincipal LocalUser user,@RequestBody LocalUser usr) {
    if (user.getRole()) {
      localUserDAO.delete(usr);
      return ResponseEntity.status(HttpStatus.OK).build();
    }
    else
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

}
