package unical.informatica.it.enterpriseapplicationbackend.api.controller.auth;

import unical.informatica.it.enterpriseapplicationbackend.api.model.*;
import unical.informatica.it.enterpriseapplicationbackend.exception.EmailFailureException;
import unical.informatica.it.enterpriseapplicationbackend.exception.EmailNotFoundException;
import unical.informatica.it.enterpriseapplicationbackend.exception.UserAlreadyExistsException;
import unical.informatica.it.enterpriseapplicationbackend.exception.UserNotVerifiedException;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.service.JWTService;
import unical.informatica.it.enterpriseapplicationbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//CONTROLLER FUNZIONANTE PER INTERO
/**
 * Rest Controller for handling authentication requests.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  /** The user service. */
  private final UserService userService;
  /** The jwt service. */
  private final JWTService jwtService;

  private final String SUCCESS = Files.readString(Paths.get("src/main/resources/templates/verification_success.html"));
  private final String FAILURE = Files.readString(Paths.get("src/main/resources/templates/verification_failure.html"));

  /**
   * Spring injected constructor.
   * @param userService The user service.
   * @param jwtService The JWT service.
   */
  public AuthenticationController(UserService userService, JWTService jwtService) throws IOException {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  /**
   * Post Mapping to handle registering users.
   * @param registrationBody The registration information.
   * @return Response to front end.
   */
  @PostMapping("/register")
  public RegistrationBody registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
    try {
      userService.registerUser(registrationBody);
      return registrationBody;
    } catch (UserAlreadyExistsException | EmailFailureException ex) {
      return registrationBody;
    }
  }

  /**
   * Post Mapping to handle user logins to provide authentication token.
   * @param loginBody The login information.
   * @return The authentication token if successful.
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
    String jwt = null;
    try {
      jwt = userService.loginUser(loginBody);
    } catch (UserNotVerifiedException ex) {
      LoginResponse response = new LoginResponse();
      response.setSuccess(false);
      String reason = "USER_NOT_VERIFIED";
      if (ex.isNewEmailSent()) {
        reason += "_EMAIL_RESENT";
      }
      response.setFailureReason(reason);
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    } catch (EmailFailureException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    if (jwt == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      LoginResponse response = new LoginResponse();
      response.setJwt(jwt);
      response.setSuccess(true);
      return ResponseEntity.ok(response);
    }
  }

  /**
   * Get mapping to verify the email of an account using the emailed token.
   *
   * @param token The token emailed for verification. This is not the same as a
   *              authentication JWT.
   * @return HTML page indicating the verification status.
   */
  @GetMapping("/verify")
  public String verifyEmail(@RequestParam String token) {
    if (userService.verifyUser(token)) {
      return SUCCESS; // Name of the HTML file for success
    } else {
      return FAILURE; // Name of the HTML file for failure
    }
  }

  /**
   * Gets the profile of the currently logged-in user and returns it.
   * @param user The authentication principal object.
   * @return The user profile.
   */
  @GetMapping("/me")
  public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
    return user;
  }

  /**
   * Sends an email to the user with a link to reset their password.
   * @param email The email to reset.
   * @return Ok if sent, bad request if email not found.
   */
  @PostMapping("/forgot")
  public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
    try {
      userService.forgotPassword(email);
      return ResponseEntity.ok().build();
    } catch (EmailNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (EmailFailureException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Resets the users password with the given token and password.
   * @param body The information for the password reset.
   * @return Okay if password was set.
   */
  @PostMapping("/reset")
  public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetBody body) {
    userService.resetPassword(body);
    return ResponseEntity.ok().build();
  }

  /**
   * Post mapping to update the profile of the logged-in user.
   * @param userUpdateBody The updated user information.
   * @param currentUser The currently authenticated user.
   * @return ResponseEntity indicating the outcome.
   */
  @PostMapping("/update")
  public ResponseEntity<String> updateUserProfile(
          @Valid @RequestBody UserUpdateBody userUpdateBody,
          @AuthenticationPrincipal LocalUser currentUser) {
    try {
      userService.updateUserProfile(currentUser, userUpdateBody);
      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile");
    }
  }
}
