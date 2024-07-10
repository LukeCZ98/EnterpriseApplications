package unical.informatica.it.enterpriseapplicationbackend.service;

import org.springframework.cglib.core.Local;
import unical.informatica.it.enterpriseapplicationbackend.api.model.LoginBody;
import unical.informatica.it.enterpriseapplicationbackend.api.model.PasswordResetBody;
import unical.informatica.it.enterpriseapplicationbackend.api.model.RegistrationBody;
import unical.informatica.it.enterpriseapplicationbackend.api.model.UserUpdateBody;
import unical.informatica.it.enterpriseapplicationbackend.exception.EmailFailureException;
import unical.informatica.it.enterpriseapplicationbackend.exception.EmailNotFoundException;
import unical.informatica.it.enterpriseapplicationbackend.exception.UserAlreadyExistsException;
import unical.informatica.it.enterpriseapplicationbackend.exception.UserNotVerifiedException;
import unical.informatica.it.enterpriseapplicationbackend.model.Address;
import unical.informatica.it.enterpriseapplicationbackend.model.LocalUser;
import unical.informatica.it.enterpriseapplicationbackend.model.Role;
import unical.informatica.it.enterpriseapplicationbackend.model.VerificationToken;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.AddressDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.LocalUserDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.ProductDAO;
import unical.informatica.it.enterpriseapplicationbackend.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Service for handling user actions.
 */
@Service
public class UserService {


  /** The LocalUserDAO. */
  private LocalUserDAO localUserDAO;
  /** The VerificationTokenDAO. */
  private VerificationTokenDAO verificationTokenDAO;
  /** The encryption service. */
  private EncryptionService encryptionService;
  /** The JWT service. */
  private JWTService jwtService;
  /** The email service. */
  private EmailService emailService;

  private AddressService addressService;

  /**
   * Constructor injected by spring.
   *
   * @param localUserDAO
   * @param verificationTokenDAO
   * @param encryptionService
   * @param jwtService
   * @param emailService
   */
  public UserService(LocalUserDAO localUserDAO, VerificationTokenDAO verificationTokenDAO, EncryptionService encryptionService,
                     JWTService jwtService, EmailService emailService, AddressService addressService, ProductDAO productDAO) {
    this.localUserDAO = localUserDAO;
    this.verificationTokenDAO = verificationTokenDAO;
    this.encryptionService = encryptionService;
    this.jwtService = jwtService;
    this.emailService = emailService;
      this.addressService = addressService;
  }

  /**
   * Attempts to register a user given the information provided.
   * @param registrationBody The registration information.
   * @return The local user that has been written to the database.
   * @throws UserAlreadyExistsException Thrown if there is already a user with the given information.
   */
  public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
    if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException();
    }
    LocalUser user = new LocalUser();
    user.setEmail(registrationBody.getEmail());
    user.setUsername(registrationBody.getUsername());
    user.setFirstName(registrationBody.getFirstName());
    user.setLastName(registrationBody.getLastName());
    user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
    user.setRole(false);
    VerificationToken verificationToken = createVerificationToken(user);
    emailService.sendVerificationEmail(verificationToken);
    return localUserDAO.save(user);
  }

  /**
   * Creates a VerificationToken object for sending to the user.
   * @param user The user the token is being generated for.
   * @return The object created.
   */
  private VerificationToken createVerificationToken(LocalUser user) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(jwtService.generateVerificationJWT(user));
    verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
    verificationToken.setUser(user);
    user.getVerificationTokens().add(verificationToken);
    return verificationToken;
  }

  /**
   * Logins in a user and provides an authentication token back.
   * @param loginBody The login request.
   * @return The authentication token. Null if the request was invalid.
   */
  public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
    Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
    if (opUser.isPresent()) {
      LocalUser user = opUser.get();
      if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
        if (user.isEmailVerified()) {
          return jwtService.generateJWT(user);
        } else {
          List<VerificationToken> verificationTokens = user.getVerificationTokens();
          boolean resend = verificationTokens.size() == 0 ||
                  verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
          if (resend) {
            VerificationToken verificationToken = createVerificationToken(user);
            verificationTokenDAO.save(verificationToken);
            emailService.sendVerificationEmail(verificationToken);
          }
          throw new UserNotVerifiedException(resend);
        }
      }
    }
    return null;
  }

  /**
   * Verifies a user from the given token.
   * @param token The token to use to verify a user.
   * @return True if it was verified, false if already verified or token invalid.
   */
  @Transactional
  public boolean verifyUser(String token) {
    Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
    if (opToken.isPresent()) {
      VerificationToken verificationToken = opToken.get();
      LocalUser user = verificationToken.getUser();
      if (!user.isEmailVerified()) {
        user.setEmailVerified(true);
        localUserDAO.save(user);
        verificationTokenDAO.deleteByUser(user);
        return true;
      }
    }
    return false;
  }
  ..

